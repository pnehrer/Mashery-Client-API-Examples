package com.mashery.examples.api.client.etsy.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class Shop extends JavaScriptObject {

	protected Shop() {
		super();
	}
	
	public final native int getShopId() /*-{
		return this.shop_id;
	}-*/;
	
	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	public final native String getFirstLine() /*-{
		return this.first_line;
	}-*/;
	
	public final native String getSecondLine() /*-{
		return this.second_line;
	}-*/;
	
	public final native String getCity() /*-{
		return this.city;
	}-*/;
	
	public final native String getState() /*-{
		return this.state;
	}-*/;
	
	public final native String getZip() /*-{
		return this.zip;
	}-*/;
	
	public final native int getCountryId() /*-{
		return this.country_id;
	}-*/;
	
	public final native int getUserId() /*-{
		return this.user_id;
	}-*/;
	
	public final native double getCreationTSZ() /*-{
		return this.creation_tsz;
	}-*/;
	
	public final native String getShopName() /*-{
		return this.shop_name;
	}-*/;
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
	
	public final native String getAnnouncement() /*-{
		return this.announcement;
	}-*/;
	
	public final native String getCurrencyCode() /*-{
		return this.currency_code;
	}-*/;
	
	public final native boolean isVacation() /*-{
		return this.is_vacation;
	}-*/;
	
	public final native String getVacationMessage() /*-{
		return this.vacation_message;
	}-*/;
	
	public final native int getSaleMessage() /*-{
		return this.sale_message;
	}-*/;
	
	public final native double getLastUpdatedTSZ() /*-{
		return this.last_updated_tsz;
	}-*/;
	
	public final native int getListingActiveCount() /*-{
		return this.listing_active_count;
	}-*/;
	
	public final native String getLoginName() /*-{
		return this.login_name;
	}-*/;
	
	public final native double getLat() /*-{
		return this.lat;
	}-*/;
	
	public final native double getLon() /*-{
		return this.lon;
	}-*/;
	
	public final native boolean hasLatLon() /*-{
		return this.lat != undefined && this.lon != undefined;
	}-*/;
}
