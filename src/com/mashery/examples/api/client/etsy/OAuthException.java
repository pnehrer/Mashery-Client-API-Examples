package com.mashery.examples.api.client.etsy;

public class OAuthException extends Exception {

	private static final long serialVersionUID = 1L;

	public OAuthException() {
		super();
	}

	public OAuthException(String s) {
		super(s);
	}

	public OAuthException(Throwable throwable) {
		super(throwable);
	}

	public OAuthException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
