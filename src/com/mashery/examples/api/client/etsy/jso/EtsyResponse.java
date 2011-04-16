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
import com.google.gwt.core.client.JsArray;

public class EtsyResponse<T extends JavaScriptObject> extends JavaScriptObject {

	protected EtsyResponse() {
		super();
	}
	
	public final native int getCount() /*-{
		return this.count;
	}-*/;
	
	public final native JsArray<T> getResults() /*-{
		return this.results;
	}-*/;
	
	public final native JavaScriptObject getParams() /*-{
		return this.params;
	}-*/;
	
	public final native String getType() /*-{
		return this.type;
	}-*/;
	
	public final native boolean isOk() /*-{
		return this.ok;
	}-*/;
	
	public final native int getStatus() /*-{
		return this.status;
	}-*/;
	
	public final native String getError() /*-{
		return this.error;
	}-*/;
}
