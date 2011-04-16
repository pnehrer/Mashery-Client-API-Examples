package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class EtsyResponse<T extends JavaScriptObject> extends JavaScriptObject {

	protected EtsyResponse() {
		super();
	}
	
	public final native int getCount() /*-{
		return this.count;
	}-*/;
	
	public final native JsArray<T> getResults() /*-{
		return this.results;
	}-*/;
	
	public final native JavaScriptObject getParams() /*-{
		return this.params;
	}-*/;
	
	public final native String getType() /*-{
		return this.type;
	}-*/;
	
	public final native boolean isOk() /*-{
		return this.ok;
	}-*/;
	
	public final native int getStatus() /*-{
		return this.status;
	}-*/;
	
	public final native String getError() /*-{
		return this.error;
	}-*/;
}
