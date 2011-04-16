package com.mashery.examples.api.client.etsy;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("etsy/etsy")
public interface EtsyService extends RemoteService {
	
	User getUser() throws EtsyException, OAuthException, NotLoggedInException;
	
	void deleteAccessTokens() throws NotLoggedInException;

	FavoriteListing createUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException;

	void deleteUserFavoriteListing(int listingId) throws EtsyException, OAuthException, NotLoggedInException;
}
