package com.mashery.examples.api.client.hotwire.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class HotwireResponse extends JavaScriptObject {

	protected HotwireResponse() {
		super();
	}
	
	public final native JsArray<Error> getErrors() /*-{
		return this.errors;
	}-*/;

	public final native int getStatusCode() /*-{
		return this.statusCode;
	}-*/;
	
	public final native String getStatusDesc() /*-{
		return this.statusDesc;
	}-*/;
	
	public final native JsArray<HotelDeal> getHotelDeals() /*-{
		return this.hotelDeals;
	}-*/;
	
	public static final native HotwireResponse fromSource(String json) /*-{
		return eval('(' + json + ')');
	}-*/;
}
