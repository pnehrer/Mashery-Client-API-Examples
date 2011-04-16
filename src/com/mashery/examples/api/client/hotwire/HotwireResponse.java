package com.mashery.examples.api.client.hotwire;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Hotwire")
@XmlAccessorType(XmlAccessType.FIELD)
public class HotwireResponse implements Serializable {

	private static final long serialVersionUID = -5223588985688986381L;

	@XmlElementWrapper(name="Errors")
	@XmlElement(name="Error")
	private Error[] errors;
	
	@XmlElement(name="StatusCode")
	private int statusCode;
	
	@XmlElement(name="StatusDesc")
	private String statusDesc; 
	
	@XmlElementWrapper(name="Result")
	@XmlElement(name="HotelDeal")
	private HotelDeal[] hotelDeals;

	public Error[] getErrors() {
		return errors;
	}

	public void setErrors(Error[] errors) {
		this.errors = errors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public HotelDeal[] getHotelDeals() {
		return hotelDeals;
	}

	public void setHotelDeals(HotelDeal[] hotelDeals) {
		this.hotelDeals = hotelDeals;
	}
}
