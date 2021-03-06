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

public class FeaturedListing extends JavaScriptObject {

	protected FeaturedListing() {
		super();
	}
	
	public final native int getFeaturedListingId() /*-{
		return this.featured_listing_id;
	}-*/;
	
	public final native int getListingId() /*-{
		return this.listing_id;
	}-*/;
	
	public final native double getActiveTime() /*-{
		return this.active_time;
	}-*/;
	
	public final native Listing getListing() /*-{
		return this.Listing;
	}-*/;
}
