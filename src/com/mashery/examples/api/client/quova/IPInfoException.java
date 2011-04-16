package com.mashery.examples.api.client.quova;

public class IPInfoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int statusCode;

	public IPInfoException() {
		super();
	}

	public IPInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IPInfoException(String message) {
		super(message);
	}

	public IPInfoException(Throwable cause) {
		super(cause);
	}

	public IPInfoException(int code, String message) {
		this(message);
		statusCode = code;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
