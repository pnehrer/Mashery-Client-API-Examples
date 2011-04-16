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
public class ListingImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("listing_image_id")
	private int listingImageId;
	
	@JsonProperty("hex_code")
	private String hexCode;
	
	private int red;
	
	private int green;
	
	private int blue;
	
	private int hue;
	
	private int saturation;
	
	private int brightness;
	
	@JsonProperty("black_and_white")
	private boolean blackAndWhite;
	
	@JsonProperty("creation_tsz")
	private double creationTSZ;
	
	@JsonProperty("listing_id")
	private int listingId;
	
	private int rank;
	
	@JsonProperty("url_75x75")
	private String url75x75;
	
	@JsonProperty("url_170x135")
	private String url170x135;
	
	@JsonProperty("url_570xN")
	private String url570xN;
	
	@JsonProperty("url_fullxfull")
	private String urlFullxFull;

	public int getListingImageId() {
		return listingImageId;
	}

	public void setListingImageId(int listingImageId) {
		this.listingImageId = listingImageId;
	}

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getHue() {
		return hue;
	}

	public void setHue(int hue) {
		this.hue = hue;
	}

	public int getSaturation() {
		return saturation;
	}

	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public boolean isBlackAndWhite() {
		return blackAndWhite;
	}

	public void setBlackAndWhite(boolean blackAndWhite) {
		this.blackAndWhite = blackAndWhite;
	}

	public double getCreationTSZ() {
		return creationTSZ;
	}

	public void setCreationTSZ(double creationTSZ) {
		this.creationTSZ = creationTSZ;
	}

	public int getListingId() {
		return listingId;
	}

	public void setListingId(int listingId) {
		this.listingId = listingId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getURL75x75() {
		return url75x75;
	}

	public void setURL75x75(String url75x75) {
		this.url75x75 = url75x75;
	}

	public String getURL170x135() {
		return url170x135;
	}

	public void setURL170x135(String url170x135) {
		this.url170x135 = url170x135;
	}

	public String getURL570xN() {
		return url570xN;
	}

	public void setURL570xN(String url570xN) {
		this.url570xN = url570xN;
	}

	public String getURLFullxFull() {
		return urlFullxFull;
	}

	public void setURLFullxFull(String urlFullxFull) {
		this.urlFullxFull = urlFullxFull;
	}
}
