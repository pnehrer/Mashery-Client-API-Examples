package com.mashery.examples.api.client.active;

import java.io.Serializable;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int num;

	private String url;
	
	private String title;
	
	private Meta meta;
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Meta getMeta() {
		return meta;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
