package com.mashery.examples.api.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void getUserEmail(AsyncCallback<String> callback);
}
