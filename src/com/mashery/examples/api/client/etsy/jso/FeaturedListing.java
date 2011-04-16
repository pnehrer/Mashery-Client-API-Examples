package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class FeaturedListing extends JavaScriptObject {

	protected FeaturedListing() {
		super();
	}
	
	public final native int getFeaturedListingId() /*-{
		return this.featured_listing_id;
	}-*/;
	
	public final native int getListingId() /*-{
		return this.listing_id;
	}-*/;
	
	public final native double getActiveTime() /*-{
		return this.active_time;
	}-*/;
	
	public final native Listing getListing() /*-{
		return this.Listing;
	}-*/;
}
