package com.mashery.examples.api.client.weatherbug;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="weather", namespace="http://www.aws.com/aws")
@XmlAccessorType(XmlAccessType.FIELD)
public class Weather implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="forecasts", namespace="http://www.aws.com/aws")
	private Forecasts forecasts;
	
	public Forecasts getForecasts() {
		return forecasts;
	}
	
	public void setForecasts(Forecasts forecasts) {
		this.forecasts = forecasts;
	}
}
