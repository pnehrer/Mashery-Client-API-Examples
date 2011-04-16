/*
 * Copyright (c) 2010, 2011 Mashery, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
