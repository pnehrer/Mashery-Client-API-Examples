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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ipinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class IPInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String region;
	
	@XmlElement(name="ip_type")
	private String ipType;
	
	@XmlElement(name="area_code")
	private String areaCode;

	private String state;
	
	@XmlElement(name="connection_type")
	private String connectionType;

	private String city;
	
	@XmlElement(name="line_speed")
	private String lineSpeed;

	private String organization;

	@XmlElement(name="country_cf")
	private int countryCF;
	
	@XmlElement(name="anonymizer_status")
	private String anonymizerStatus;

	private String carrier;

	private String sld;
	
	private int msa;
	
	private double longitude;
	
	@XmlElement(name="state_cf")
	private int stateCF;
	
	@XmlElement(name="state_code")
	private String stateCode;
	
	private int dma;
	
	@XmlElement(name="country_code")
	private String countryCode;

	@XmlElement(name="ip_routing_type")
	private String ipRoutingType;

	private String country;
	
	@XmlElement(name="time_zone")
	private int timeZone;
	
	@XmlElement(name="ip_address")
	private String ipAddress;

	@XmlElement(name="postal_code")
	private String postalCode;

	private String continent;
	
	private int asn;
	
	@XmlElement(name="city_cf")
	private int cityCF;
	
	private double latitude;
	
	private String tld;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(String lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getCountryCF() {
		return countryCF;
	}

	public void setCountryCF(int countryCF) {
		this.countryCF = countryCF;
	}

	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getSld() {
		return sld;
	}

	public void setSld(String sld) {
		this.sld = sld;
	}

	public int getMsa() {
		return msa;
	}

	public void setMsa(int msa) {
		this.msa = msa;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getStateCF() {
		return stateCF;
	}

	public void setStateCF(int stateCF) {
		this.stateCF = stateCF;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public int getDma() {
		return dma;
	}

	public void setDma(int dma) {
		this.dma = dma;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getIpRoutingType() {
		return ipRoutingType;
	}

	public void setIpRoutingType(String ipRoutingType) {
		this.ipRoutingType = ipRoutingType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public int getAsn() {
		return asn;
	}

	public void setAsn(int asn) {
		this.asn = asn;
	}

	public int getCityCF() {
		return cityCF;
	}

	public void setCityCF(int cityCF) {
		this.cityCF = cityCF;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getTld() {
		return tld;
	}

	public void setTld(String tld) {
		this.tld = tld;
	}
}
