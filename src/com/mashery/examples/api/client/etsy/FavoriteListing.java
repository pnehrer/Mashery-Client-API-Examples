package com.mashery.examples.api.client.etsy;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FavoriteListing implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("favorite_listing_id")
	private int favoriteListingId;
	
	@JsonProperty("user_id")
	private int userId;
	
	@JsonProperty("listing_id")
	private int listingId;
	
	@JsonProperty("creation_tsz")
	private long creationTSZ;
	
	private String state;
	
	@JsonProperty("Listing")
	private Listing listing;

	public int getFavoriteListingId() {
		return favoriteListingId;
	}

	public void setFavoriteListingId(int favoriteListingId) {
		this.favoriteListingId = favoriteListingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public long getCreationTSZ() {
		return creationTSZ;
	}

	public void setCreationTSZ(long creationTSZ) {
		this.creationTSZ = creationTSZ;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}
}
