/*
 * Copyright (c) 2010, 2011 Mashery, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mashery.examples.api.server.etsy;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mashery.examples.api.server.Persistence;

@SuppressWarnings("serial")
public class OAuthAccessTokenServlet extends HttpServlet {
	
	private String etsyService;
	
	private String etsyEndpoint;
	
	private String etsyApikey;

	private String etsySecret;
	
	@Override
	public void init() throws ServletException {
		super.init();
		etsyService = getInitParameter("etsy.service");
		etsyEndpoint = getInitParameter("etsy.endpoint");
		etsyApikey = getInitParameter("etsy.apikey");
		etsySecret = getInitParameter("etsy.secret");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserService userSvc = UserServiceFactory.getUserService();
		if (!userSvc.isUserLoggedIn()) {
			resp.sendError(403, "Not logged in.");
			return;
		}

		String requestToken = req.getParameter("oauth_token");
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		String secret = (String) memcache.get("OAuthRequest_" + requestToken);
		String callbackURL = (String) memcache.get("OAuthCallback_" + requestToken);
	
		String verifier = req.getParameter("oauth_verifier");
		
		OAuthProvider provider = new DefaultOAuthProvider(null, etsyEndpoint, "");
		provider.setOAuth10a(true);
		OAuthConsumer consumer = new DefaultOAuthConsumer(etsyApikey, etsySecret);
		consumer.setTokenWithSecret(requestToken, secret);
		try {
			provider.retrieveAccessToken(consumer, verifier);
		} catch (OAuthMessageSignerException e) {
			throw new ServletException(e);
		} catch (OAuthNotAuthorizedException e) {
			throw new ServletException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new ServletException(e);
		} catch (OAuthCommunicationException e) {
			throw new ServletException(e);
		}

		User user = userSvc.getCurrentUser();
		PersistenceManager pm = Persistence.getPMF().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			Query query = pm.newQuery(OAuthAccessToken.class, "service == :service && userId == :userId");
			query.deletePersistentAll(etsyService, user.getUserId());

			OAuthAccessToken accessToken = new OAuthAccessToken(etsyService, user.getUserId(), consumer.getToken(), consumer.getTokenSecret());
			pm.makePersistent(accessToken);
			
			tx.commit();
		} finally {
			if (tx.isActive())
				tx.rollback();
			
			pm.close();
		}
		
		resp.sendRedirect(callbackURL);
	}
}
