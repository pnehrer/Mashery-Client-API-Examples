package com.mashery.examples.api.client.active;

import java.io.Serializable;
import java.util.Date;

public class Meta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date eventDate;
	
	private String location;
	
	private String eventAddress;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	private String country;
	
	private String assetName;
	
	private double latitude;
	
	private double longitude;
	
	private String image1;

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getImage1() {
		return image1;
	}
	
	public void setImage1(String image1) {
		this.image1 = image1;
	}
}
