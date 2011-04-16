package com.mashery.examples.api.client.weatherbug;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Forecast implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(namespace="http://www.aws.com/aws")
	private String title;
	
//	@XmlElement(name="short-prediction")
	@XmlElement(namespace="http://www.aws.com/aws")
	private String shortPrediction;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String image;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String description;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private String prediction;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private int high;
	
	@XmlElement(namespace="http://www.aws.com/aws")
	private int low;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortPrediction() {
		return shortPrediction;
	}

	public void setShortPrediction(String shortPrediction) {
		this.shortPrediction = shortPrediction;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}
}
