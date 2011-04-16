package com.mashery.examples.api.client.hotwire.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class HotelDeal extends JavaScriptObject {

	protected HotelDeal() {
		super();
	}
	
	public final native String getCity() /*-{
		return this.city;
	}-*/;
	
	public final native String getCountryCode() /*-{
		return this.countryCode;
	}-*/;
	
	public final native double getFoundDate() /*-{
		return this.foundDate;
	}-*/;
	
	public final native double getNightDuration() /*-{
		return this.nightDuration;
	}-*/;
	
	public final native double getEndDate() /*-{
		return this.endDate;
	}-*/;
	
	public final native String getHeadline() /*-{
		return this.headline;
	}-*/;
	
	public final native boolean isWeekendStay() /*-{
		return this.weekendStay;
	}-*/;
	
	public final native double getNeighborhoodLatitude() /*-{
		return this.neighborhoodLatitude;
	}-*/;
	
	public final native double getNeighborhoodLongitude() /*-{
		return this.neighborhoodLongitude;
	}-*/;
	
	public final native String getNeighborhood() /*-{
		return this.neighborhood;
	}-*/;
	
	public final native double getPrice() /*-{
		return this.price;
	}-*/;
	
	public final native double getStarRating() /*-{
		return this.starRating;
	}-*/;
	
	public final native double getStartDate() /*-{
		return this.startDate;
	}-*/;
	
	public final native String getStateCode() /*-{
		return this.stateCode;
	}-*/;
	
	public final native String getUrl() /*-{
		return this.url;
	}-*/;
}
