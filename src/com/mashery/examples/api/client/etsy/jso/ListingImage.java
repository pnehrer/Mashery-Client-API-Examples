package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class ListingImage extends JavaScriptObject {

	protected ListingImage() {
		super();
	}
	
	public final native int getListingImageId() /*-{
		return this.listing_image_id;
	}-*/;
	
	public final native String getHexCode() /*-{
		return this.hex_code;
	}-*/;
	
	public final native int getRed() /*-{
		return this.red;
	}-*/;
	
	public final native int getGreen() /*-{
		return this.green;
	}-*/;
	
	public final native int getBlue() /*-{
		return this.blue;
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
	
	public final native double getCreationTSZ() /*-{
		return this.creation_tsz;
	}-*/;
	
	public final native int getListingId() /*-{
		return this.listing_id;
	}-*/;
	
	public final native int getRank() /*-{
		return this.rank;
	}-*/;
	
	public final native String getURL75x75() /*-{
		return this.url_75x75;
	}-*/;
	
	public final native String getURL170x135() /*-{
		return this.url_170x135;
	}-*/;
	
	public final native String getURL570xN() /*-{
		return this.url_570xN;
	}-*/;
	
	public final native String getURLFullxFull() /*-{
		return this.url_fullxfull;
	}-*/;
}
