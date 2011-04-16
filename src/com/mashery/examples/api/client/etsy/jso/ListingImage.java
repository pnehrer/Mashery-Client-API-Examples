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
