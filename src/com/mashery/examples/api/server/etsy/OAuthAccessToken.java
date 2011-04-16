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
package com.mashery.examples.api.server.etsy;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class OAuthAccessToken {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	private String service;
	
	private String userId;
	
	private String token;
	
	private String secret;
	
	public OAuthAccessToken(String service, String userId, String token, String secret) {
		this.service = service;
		this.userId = userId;
		this.token = token;
		this.secret = secret;
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getService() {
		return service;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}
	
	public String getSecret() {
		return secret;
	}
}
