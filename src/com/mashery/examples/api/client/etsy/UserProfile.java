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
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("user_profile_id")
	private int userProfileId;
	
	@JsonProperty("user_id")
	private int userId;
	
	@JsonProperty("login_name")
	private String loginName;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	private String bio;
	
	private String gender;
	
	@JsonProperty("birth_month")
	private String birthMonth;
	
	@JsonProperty("birth_day")
	private String birthDay;
	
	@JsonProperty("birth_year")
	private String birthYear;
	
	@JsonProperty("join_tsz")
	private double joinTSZ;
	
	private String materials;
	
	@JsonProperty("country_id")
	private int countryId;
	
	private String city;
	
	@JsonProperty("avatar_id")
	private int avatarId;

	private double lat;
	
	private double lon;

	@JsonProperty("transaction_buy_count")
	private int transactionBuyCount;
	
	@JsonProperty("transaction_sell_count")
	private int transactionSellCount;
	
	@JsonProperty("is_seller")
	private boolean seller;
	
	@JsonProperty("image_url_75x75")
	private String imageURL75x75;

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public double getJoinTSZ() {
		return joinTSZ;
	}

	public void setJoinTSZ(double joinTSZ) {
		this.joinTSZ = joinTSZ;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getTransactionBuyCount() {
		return transactionBuyCount;
	}

	public void setTransactionBuyCount(int transactionBuyCount) {
		this.transactionBuyCount = transactionBuyCount;
	}

	public int getTransactionSellCount() {
		return transactionSellCount;
	}

	public void setTransactionSellCount(int transactionSellCount) {
		this.transactionSellCount = transactionSellCount;
	}

	public boolean isSeller() {
		return seller;
	}

	public void setSeller(boolean seller) {
		this.seller = seller;
	}

	public String getImageURL75x75() {
		return imageURL75x75;
	}

	public void setImageURL75x75(String imageURL75x75) {
		this.imageURL75x75 = imageURL75x75;
	}
}
