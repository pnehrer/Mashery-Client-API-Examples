package com.mashery.examples.api.server.quova;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gds_error")
@XmlAccessorType(XmlAccessType.FIELD)
public class GDSError {

	@XmlElement(name="http_status")
	private int httpStatus;
	
	@XmlElement(name="message")
	private String message;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
