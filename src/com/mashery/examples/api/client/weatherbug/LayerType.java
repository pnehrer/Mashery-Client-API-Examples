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
