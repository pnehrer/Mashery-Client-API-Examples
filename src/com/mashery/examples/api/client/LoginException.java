package com.mashery.examples.api.client;

public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginException() {
		super();
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}
}
