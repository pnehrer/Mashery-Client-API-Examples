package com.mashery.examples.api.client.etsy;

public class NotLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(String s) {
		super(s);
	}

	public NotLoggedInException(Throwable throwable) {
		super(throwable);
	}

	public NotLoggedInException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
