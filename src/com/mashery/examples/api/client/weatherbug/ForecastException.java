package com.mashery.examples.api.client.weatherbug;

public class ForecastException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int statusCode;

	public ForecastException() {
		super();
	}

	public ForecastException(String message) {
		super(message);
	}

	public ForecastException(Throwable cause) {
		super(cause);
	}

	public ForecastException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForecastException(int statusCode, String statusMessage) {
		super(statusMessage);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
