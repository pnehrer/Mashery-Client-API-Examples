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

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class OAuthRequestTokenServlet extends HttpServlet {

	private String etsyEndpoint;
	
	private String etsyLoginURL;
	
	private String etsyApikey;

	private String etsySecret;
	
	@Override
	public void init() throws ServletException {
		super.init();
		etsyEndpoint = getInitParameter("etsy.endpoint");
		etsyLoginURL = getInitParameter("etsy.loginURL");
		etsyApikey = getInitParameter("etsy.apikey");
		etsySecret = getInitParameter("etsy.secret");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuilder callbackBuf = new StringBuilder(req.getScheme());
		callbackBuf.append("://").append(req.getHeader("Host"));
		callbackBuf.append("/examples/etsy/oauth/access_token");
		
		OAuthProvider provider = new DefaultOAuthProvider(etsyEndpoint, null, etsyLoginURL);
		OAuthConsumer consumer = new DefaultOAuthConsumer(etsyApikey, etsySecret);
		String loginURL;
		try {
			loginURL = provider.retrieveRequestToken(consumer, callbackBuf.toString());
		} catch (OAuthMessageSignerException e) {
			throw new ServletException(e);
		} catch (OAuthNotAuthorizedException e) {
			throw new ServletException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new ServletException(e);
		} catch (OAuthCommunicationException e) {
			throw new ServletException(e);
		}
		
		// store request token/secret temporarily
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		memcache.put("OAuthRequest_" + consumer.getToken(), consumer.getTokenSecret(), Expiration.byDeltaSeconds(300));
		
		// store callback temporarily
		String callbackURL = req.getParameter("callbackURL");
		memcache.put("OAuthCallback_" + consumer.getToken(), callbackURL, Expiration.byDeltaSeconds(301));
		
		// see if we received a different login url
		String loginURLOverride = provider.getResponseParameters().getFirst("login_url", true);
		if (loginURLOverride != null) {
			// append query to new login url
			StringBuilder buf = new StringBuilder(loginURLOverride);
			String loginQuery = loginURL.substring(loginURL.indexOf('?') + 1);
			if (loginURLOverride.indexOf('?') == -1)
				buf.append('?');
			else
				buf.append('&');
			
			buf.append(loginQuery);
			resp.sendRedirect(buf.toString());
			return;
		}

		resp.sendRedirect(loginURL);
	}
}
