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
