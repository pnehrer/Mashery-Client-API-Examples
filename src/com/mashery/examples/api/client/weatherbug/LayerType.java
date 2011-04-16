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

public enum LayerType {
	
	TEMPERATURE("Temperature", 1, "Temperature"),
	
	RADAR("Radar", 52, "Radar"),
	
	IRSAT("IRSat", 47, "Infra-Red Satellite"),
	
	HUMIDITY("Humidity", 14, "Humidity"),
	
	PRESSURE("Pressure", 17, "Pressure"),
	
	HIGH("High", 56, "High Forecast for Today"),
	
	LOW("Low", 57, "Low Forecast for Today");
	
	private final String path;
	
	private final int id;
	
	private final String description;
	
	private LayerType(String path, int id, String description) {
		this.path = path;
		this.id = id;
		this.description = description;
	}
	
	public String getPath() {
		return path;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
}
