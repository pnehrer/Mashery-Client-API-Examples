package com.mashery.examples.api.client.etsy;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Listing implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("listing_id")
	private int listingId;
	
	private String state;
	
	@JsonProperty("user_id")
	private int userId;
	
	private String title;

	@JsonProperty("Images")
	private ListingImage[] images;

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ListingImage[] getImages() {
		return images;
	}
	
	public void setImages(ListingImage[] images) {
		this.images = images;
	}
}
