package com.mashery.examples.api.client.active;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("active/search")
public interface SearchService extends RemoteService {

	SearchResponse search(String keywords, String location, int page, int size) throws SearchException;
}
