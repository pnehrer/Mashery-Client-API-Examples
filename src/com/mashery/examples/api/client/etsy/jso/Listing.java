package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class Listing extends JavaScriptObject {

	protected Listing() {
		super();
	}
	
	public final native int getListingId() /*-{
		return this.listing_id;
	}-*/;
	
	public final native String getState() /*-{
		return this.state;
	}-*/;
	
	public final native int getUserId() /*-{
		return this.user_id;
	}-*/;
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
	
	public final native String getDescription() /*-{
		return this.description;
	}-*/;
	
	public final native double getCreationTSZ() /*-{
		return this.creation_tsz;
	}-*/;
	
	public final native double getEndingTSZ() /*-{
		return this.ending_tsz;
	}-*/;
	
	public final native double getOriginalCreationTSZ() /*-{
		return this.original_creation_tsz;
	}-*/;
	
	public final native double getLastModifiedTSZ() /*-{
		return this.last_modified_tsz;
	}-*/;
	
	public final native double getPrice() /*-{
		return this.price;
	}-*/;
	
	public final native String getCurrencyCode() /*-{
		return this.currency_code;
	}-*/;
	
	public final native int getQuantity() /*-{
		return this.quantity;
	}-*/;
	
	public final native JsArrayString getTags() /*-{
		return this.tags;
	}-*/;
	
	public final native JsArrayString getMaterials() /*-{
		return this.materials;
	}-*/;
	
	public final native int getShopSectionId() /*-{
		return this.shop_section_id;
	}-*/;
	
	public final native int getFeaturedRank() /*-{
		return this.featured_rank;
	}-*/;
	
	public final native double getStateTSZ() /*-{
		return this.state_tsz;
	}-*/;
	
	public final native int getHue() /*-{
		return this.hue;
	}-*/;
	
	public final native int getSaturation() /*-{
		return this.saturation;
	}-*/;
	
	public final native int getBrightness() /*-{
		return this.brightness;
	}-*/;
	
	public final native boolean isBlackAndWhite() /*-{
		return this.is_black_and_white;
	}-*/;
	
	public final native String getURL() /*-{
		return this.url;
	}-*/;
	
	public final native int getViews() /*-{
		return this.views;
	}-*/;
	
	public final native JsArray<ListingImage> getImages() /*-{
		return this.Images;
	}-*/;
	
	public final native Shop getShop() /*-{
		return this.Shop;
	}-*/;
}
