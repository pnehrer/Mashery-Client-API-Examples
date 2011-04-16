package com.mashery.examples.api.client.etsy;

public class EtsyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int statusCode;

	public EtsyException() {
		super();
	}

	public EtsyException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public EtsyException(String s) {
		super(s);
	}

	public EtsyException(Throwable throwable) {
		super(throwable);
	}

	public EtsyException(int statusCode, String statusMessage) {
		this(statusMessage);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
