package com.mashery.examples.api.client.weatherbug;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Forecasts implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="location", namespace="http://www.aws.com/aws")
	private Location location;

	@XmlElement(name="forecast", namespace="http://www.aws.com/aws")
	private Forecast[] forecasts;
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Forecast[] getForecasts() {
		return forecasts;
	}
	
	public void setForecasts(Forecast[] forecasts) {
		this.forecasts = forecasts;
	}
}
