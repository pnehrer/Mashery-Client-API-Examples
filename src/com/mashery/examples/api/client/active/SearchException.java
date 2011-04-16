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
package com.mashery.examples.api.client.active;

public class SearchException extends Exception {

	private static final long serialVersionUID = 1L;
	private int statusCode;

	public SearchException() {
		super();
	}

	public SearchException(String s) {
		super(s);
	}

	public SearchException(Throwable throwable) {
		super(throwable);
	}

	public SearchException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public SearchException(int statusCode, String statusMessage) {
		super(statusMessage);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
