package com.mashery.examples.api.client.active;

public class SearchException extends Exception {

	private static final long serialVersionUID = 1L;
	private int statusCode;

	public SearchException() {
		super();
	}

	public SearchException(String s) {
		super(s);
	}

	public SearchException(Throwable throwable) {
		super(throwable);
	}

	public SearchException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public SearchException(int statusCode, String statusMessage) {
		super(statusMessage);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
