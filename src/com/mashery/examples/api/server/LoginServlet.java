package com.mashery.examples.api.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String callbackURL = req.getParameter("callbackURL");
		boolean logout = Boolean.valueOf(req.getParameter("logout"));
		UserService svc = UserServiceFactory.getUserService();
		String url;
		if (logout) {
			url = svc.createLogoutURL(callbackURL);
		} else {
			url = svc.createLoginURL(callbackURL);
		}

		resp.sendRedirect(url);
	}
}
