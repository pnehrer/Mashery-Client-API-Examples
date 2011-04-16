package com.mashery.examples.api.client.weatherbug;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(namespace="http://www.aws.com/aws")
	private String city;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String state;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String zip;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String country;

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
}
