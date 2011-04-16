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
package com.mashery.examples.api.server.active;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.xml.bind.JAXB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mashery.examples.api.client.active.SearchException;
import com.mashery.examples.api.client.active.SearchResponse;
import com.mashery.examples.api.client.active.SearchService;

@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private String activeEndpoint;
	
	private String activeApikey;

	@Override
	public void init() throws ServletException {
		super.init();
		activeEndpoint = getInitParameter("active.endpoint");
		activeApikey = getInitParameter("active.apikey");
	}

	@Override
	public SearchResponse search(String keywords, String location, int page, int size) throws SearchException {
		try {
			return doSearch(keywords, location, page, size);
		} catch (IOException e) {
			throw new SearchException(e);
		}
	}
	
	private SearchResponse doSearch(String keywords, String location, int page, int size) throws SearchException, IOException {
		// create Active Search service URL
		StringBuilder buf = new StringBuilder(activeEndpoint);
		buf.append("?v=xml&api_key=");
		buf.append(URLEncoder.encode(activeApikey, "UTF-8"));
		buf.append("&num=").append(size);
		buf.append("&page=").append(page);
		if (keywords != null)
			buf.append("&k=").append(URLEncoder.encode(keywords, "UTF-8"));
		
		if (location != null)
			buf.append("&l=").append(URLEncoder.encode(location, "UTF-8"));
		
		URL url = new URL(buf.toString());
		
		// call Active Search
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_OK) {
			String msg = conn.getResponseMessage();
			if (msg == null)
				msg = conn.getHeaderField("X-Mashery-Error-Code");
			
			throw new SearchException(rc, msg);
		}
		
		// parse response
		InputStream in = conn.getInputStream();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		return JAXB.unmarshal(ir, SearchResponse.class);
	}

	private String getContentCharset(HttpURLConnection conn) {
		String charset = null;
		String ct = conn.getContentType();
		if (ct != null) {
			String[] parts = ct.split(";");
			for (int i = 1; i < parts.length; ++i) {
				if (parts[i].trim().toLowerCase().startsWith("charset")) {
					String[] pair = parts[i].split("=", 2);
					if (pair[0].trim().equalsIgnoreCase("charset") && pair.length > 1) {
						charset = pair[1].trim();
					}
				}
			}
		}
		
		return charset;
	}
}
