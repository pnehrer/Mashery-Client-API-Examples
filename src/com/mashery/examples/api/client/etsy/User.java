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
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("user_id")
	private int userId;
	
	@JsonProperty("login_name")
	private String loginName;
	
	@JsonProperty("primary_email")
	private String primaryEmail;
	
	@JsonProperty("creation_tsz")
	private double creationTSZ;
	
	@JsonProperty("referred_by_user_id")
	private int referredByUserId;
	
	@JsonProperty("Profile")
	private UserProfile profile;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public double getCreationTSZ() {
		return creationTSZ;
	}

	public void setCreationTSZ(double creationTSZ) {
		this.creationTSZ = creationTSZ;
	}

	public int getReferredByUserId() {
		return referredByUserId;
	}

	public void setReferredByUserId(int referredByUserId) {
		this.referredByUserId = referredByUserId;
	}
	
	public UserProfile getProfile() {
		return profile;
	}
	
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
}
