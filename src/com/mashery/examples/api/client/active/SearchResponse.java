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
package com.mashery.examples.api.client.active;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="search")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int totalResults;
	
	private int fromNum;
	
	private int toNum;
	
	private int page;
	
	@XmlElementWrapper(name="results")
	@XmlElement(name="result")
	private SearchResult[] results;

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getFromNum() {
		return fromNum;
	}

	public void setFromNum(int fromNum) {
		this.fromNum = fromNum;
	}

	public int getToNum() {
		return toNum;
	}

	public void setToNum(int toNum) {
		this.toNum = toNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public SearchResult[] getResults() {
		return results;
	}

	public void setResults(SearchResult[] results) {
		this.results = results;
	}
}
