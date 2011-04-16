package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class FavoriteListing extends JavaScriptObject {

	protected FavoriteListing() {
		super();
	}
	
	public final native int getFavoriteListingId() /*-{
		return this.favorite_listing_id;
	}-*/;
	
	public final native int getUserId() /*-{
		return this.user_id;
	}-*/;
	
	public final native int getListingId() /*-{
		return this.listing_id;
	}-*/;
	
	public final native double getCreationTSZ() /*-{
		return this.creation_tsz;
	}-*/;
	
	public final native String getState() /*-{
		return this.state;
	}-*/;
	
	public final native Listing getListing() /*-{
		return this.Listing;
	}-*/;
}
