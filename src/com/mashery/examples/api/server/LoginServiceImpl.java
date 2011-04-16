package com.mashery.examples.api.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mashery.examples.api.client.LoginException;
import com.mashery.examples.api.client.LoginService;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	@Override
	public String getUserEmail() throws LoginException {
		UserService svc = UserServiceFactory.getUserService();
		if (svc.isUserLoggedIn()) {
			User user = svc.getCurrentUser();
			return user.getEmail();
		}
		
		return null;
	}
}
