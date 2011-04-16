package com.mashery.examples.api.client.etsy;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EtsyServiceAsync {

	void getUser(AsyncCallback<User> callback);

	void deleteAccessTokens(AsyncCallback<Void> callback);

	void createUserFavoriteListing(int listingId, AsyncCallback<FavoriteListing> callback);

	void deleteUserFavoriteListing(int listingId, AsyncCallback<Void> callback);
}
