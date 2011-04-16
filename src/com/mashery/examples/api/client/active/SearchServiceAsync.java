package com.mashery.examples.api.client.active;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void search(String keywords, String location, int page, int size, AsyncCallback<SearchResponse> callback);
}
