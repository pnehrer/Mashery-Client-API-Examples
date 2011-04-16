package com.mashery.examples.api.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginsvc")
public interface LoginService extends RemoteService {
	
	String getUserEmail() throws LoginException;
}
