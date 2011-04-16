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
package com.mashery.examples.api.client.hotwire;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HotelDeal implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="City")
	private String city;
	
	@XmlElement(name="CountryCode")
	private String countryCode;
	
	@XmlElement(name="FoundDate")
	private Date foundDate;
	
	@XmlElement(name="NightDuration")
	private double nightDuration;
	
	@XmlElement(name="EndDate")
	private Date endDate;
	
	@XmlElement(name="Headline")
	private String headline;
	
	@XmlElement(name="IsWeekendStay")
	private boolean weekendStay;
	
	@XmlElement(name="NeighborhoodLatitude")
	private double neighborhoodLatitude;
	
	@XmlElement(name="NeighborhoodLongitude")
	private double neighborhoodLongitude;
	
	@XmlElement(name="Neighborhood")
	private String neighborhood;
	
	@XmlElement(name="Price")
	private double price;
	
	@XmlElement(name="StarRating")
	private double starRating;
	
	@XmlElement(name="StartDate")
	private Date startDate;
	
	@XmlElement(name="StateCode")
	private String stateCode;
	
	@XmlElement(name="Url")
	private String url;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public double getNightDuration() {
		return nightDuration;
	}

	public void setNightDuration(double nightDuration) {
		this.nightDuration = nightDuration;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public boolean isWeekendStay() {
		return weekendStay;
	}

	public void setWeekendStay(boolean weekendStay) {
		this.weekendStay = weekendStay;
	}

	public double getNeighborhoodLatitude() {
		return neighborhoodLatitude;
	}

	public void setNeighborhoodLatitude(double neighborhoodLatitude) {
		this.neighborhoodLatitude = neighborhoodLatitude;
	}

	public double getNeighborhoodLongitude() {
		return neighborhoodLongitude;
	}

	public void setNeighborhoodLongitude(double neighborhoodLongitude) {
		this.neighborhoodLongitude = neighborhoodLongitude;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getStarRating() {
		return starRating;
	}

	public void setStarRating(double starRating) {
		this.starRating = starRating;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
