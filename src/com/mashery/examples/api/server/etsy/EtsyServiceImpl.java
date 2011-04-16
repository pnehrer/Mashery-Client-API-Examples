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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mashery.examples.api.client.etsy.EtsyException;
import com.mashery.examples.api.client.etsy.EtsyService;
import com.mashery.examples.api.client.etsy.FavoriteListing;
import com.mashery.examples.api.client.etsy.NotLoggedInException;
import com.mashery.examples.api.client.etsy.OAuthException;
import com.mashery.examples.api.server.Persistence;

@SuppressWarnings("serial")
public class EtsyServiceImpl extends RemoteServiceServlet implements EtsyService {
	
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
	public com.mashery.examples.api.client.etsy.User getUser() throws EtsyException, OAuthException, NotLoggedInException {
		OAuthAccessToken accessToken = getAccessToken();
		try {
			return getUser(accessToken);
		} catch (IOException e) {
			throw new EtsyException(e);
		}
	}
	
	private com.mashery.examples.api.client.etsy.User getUser(OAuthAccessToken accessToken) throws EtsyException, IOException {
		// create Etsy getUser service URL
		StringBuilder buf = new StringBuilder(etsyEndpoint);
		buf.append("/users/__SELF__?includes=Profile");
		URL url = new URL(buf.toString());
		
		// call Etsy
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		OAuthConsumer consumer = new DefaultOAuthConsumer(etsyApikey, etsySecret);
		consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
		try {
			consumer.sign(conn);
		} catch (OAuthMessageSignerException e) {
			throw new EtsyException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new EtsyException(e);
		} catch (OAuthCommunicationException e) {
			throw new EtsyException(e);
		}
		
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_OK)
			throw new EtsyException(rc, conn.getResponseMessage());
		
		// parse response
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		EtsyResponse<com.mashery.examples.api.client.etsy.User> result = mapper.readValue(ir, new TypeReference<EtsyResponse<com.mashery.examples.api.client.etsy.User>>() { });
		// if there were no results, we'd get a 404
		return result.getResults().get(0);
	}
	
	@Override
	public void deleteAccessTokens() throws NotLoggedInException {
		UserService userSvc = UserServiceFactory.getUserService();
		if (!userSvc.isUserLoggedIn())
			throw new NotLoggedInException();

		User user = userSvc.getCurrentUser();
		PersistenceManager pm = Persistence.getPMF().getPersistenceManager();
		try {
			Query query = pm.newQuery(OAuthAccessToken.class, "service == :service && userId == :userId");
			query.deletePersistentAll(etsyService, user.getUserId());
		} finally {
			pm.close();
		}
	}

	@Override
	public FavoriteListing createUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException {
		OAuthAccessToken accessToken = getAccessToken();
		try {
			return createUserFavoriteListings(listingId, accessToken);
		} catch (IOException e) {
			throw new EtsyException(e);
		}
	}
	
	private FavoriteListing createUserFavoriteListings(int listingId, OAuthAccessToken accessToken) throws EtsyException, IOException {
		// create Etsy findAllUserFavoriteListings service URL
		StringBuilder buf = new StringBuilder(etsyEndpoint);
		buf.append("/users/__SELF__");
		buf.append("/favorites/listings/").append(listingId);
		buf.append("?includes=Listing/Images");
		URL url = new URL(buf.toString());
		
		// call Etsy
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		
		OAuthConsumer consumer = new DefaultOAuthConsumer(etsyApikey, etsySecret);
		consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
		try {
			consumer.sign(conn);
		} catch (OAuthMessageSignerException e) {
			throw new EtsyException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new EtsyException(e);
		} catch (OAuthCommunicationException e) {
			throw new EtsyException(e);
		}
		
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_CREATED)
			throw new EtsyException(rc, conn.getResponseMessage());
		
		// parse response
		InputStream in = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		EtsyResponse<FavoriteListing> result = mapper.readValue(ir, new TypeReference<EtsyResponse<FavoriteListing>>() { });
		return result.getResults().get(0);
	}

	@Override
	public void deleteUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException {
		OAuthAccessToken accessToken = getAccessToken();
		try {
			deleteUserFavoriteListings(listingId, accessToken);
		} catch (IOException e) {
			throw new EtsyException(e);
		}
	}

	private void deleteUserFavoriteListings(int listingId, OAuthAccessToken accessToken) throws EtsyException, IOException {
		// create Etsy findAllUserFavoriteListings service URL
		StringBuilder buf = new StringBuilder(etsyEndpoint);
		buf.append("/users/__SELF__");
		buf.append("/favorites/listings/").append(listingId);
		URL url = new URL(buf.toString());
		
		// call Etsy
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("DELETE");
		
		OAuthConsumer consumer = new DefaultOAuthConsumer(etsyApikey, etsySecret);
		consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getSecret());
		try {
			consumer.sign(conn);
		} catch (OAuthMessageSignerException e) {
			throw new EtsyException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new EtsyException(e);
		} catch (OAuthCommunicationException e) {
			throw new EtsyException(e);
		}
		
		int rc = conn.getResponseCode();
		if (rc != HttpURLConnection.HTTP_OK)
			throw new EtsyException(rc, conn.getResponseMessage());
	}

	private OAuthAccessToken getAccessToken() throws OAuthException, NotLoggedInException {
		UserService userSvc = UserServiceFactory.getUserService();
		if (!userSvc.isUserLoggedIn())
			throw new NotLoggedInException();
		
		User user = userSvc.getCurrentUser();
		PersistenceManager pm = Persistence.getPMF().getPersistenceManager();
		OAuthAccessToken accessToken;
		try {
			Query query = pm.newQuery(OAuthAccessToken.class, "service == :service && userId == :userId");
			query.setUnique(true);
			accessToken = (OAuthAccessToken) query.execute(etsyService, user.getUserId());
		} finally {
			pm.close();
		}
		
		if (accessToken == null)
			throw new OAuthException();
		
		return accessToken;
	}

	private String getContentCharset(HttpURLConnection conn) {
		String charset = null;
		String ct = conn.getContentType();
		if (ct != null) {
			String[] parts = ct.split(";");
			for (int i = 1; i < parts.length; ++i) {
				if (parts[i].trim().toLowerCase().startsWith("charset")) {
					String[] pair = parts[i].split("=", 2);
					if (pair[0].trim().equalsIgnoreCase("charset") && pair.length > 1) {
						charset = pair[1].trim();
					}
				}
			}
		}
		
		return charset;
	}
}
