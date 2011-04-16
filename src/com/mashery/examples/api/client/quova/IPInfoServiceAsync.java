package com.mashery.examples.api.client.quova;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IPInfoServiceAsync {

	void getIPInfo(String ip, AsyncCallback<IPInfo> callback);
}
