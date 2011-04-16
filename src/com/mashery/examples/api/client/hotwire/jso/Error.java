package com.mashery.examples.api.client.hotwire.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class Error extends JavaScriptObject {

	protected Error() {
		super();
	}
	
	public final native int getErrorCode() /*-{
		return this.errorCode;
	}-*/;
	
	public final native String getErrorMessage() /*-{
		return this.errorMessage;
	}-*/;
}
