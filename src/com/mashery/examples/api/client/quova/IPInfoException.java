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
package com.mashery.examples.api.client.quova;

public class IPInfoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int statusCode;

	public IPInfoException() {
		super();
	}

	public IPInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public IPInfoException(String message) {
		super(message);
	}

	public IPInfoException(Throwable cause) {
		super(cause);
	}

	public IPInfoException(int code, String message) {
		this(message);
		statusCode = code;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
