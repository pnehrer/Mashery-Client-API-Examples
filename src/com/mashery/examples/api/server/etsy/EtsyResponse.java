package com.mashery.examples.api.server.etsy;

import java.util.List;
import java.util.Map;

public class EtsyResponse<T> {

	private int count;
	
	private List<T> results;
	
	private Map<String, Object> params;
	
	private String type;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
