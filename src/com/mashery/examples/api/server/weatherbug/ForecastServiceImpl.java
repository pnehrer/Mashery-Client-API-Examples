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
package com.mashery.examples.api.server.weatherbug;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.xml.bind.JAXB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mashery.examples.api.client.weatherbug.ForecastException;
import com.mashery.examples.api.client.weatherbug.ForecastService;
import com.mashery.examples.api.client.weatherbug.Weather;

@SuppressWarnings("serial")
public class ForecastServiceImpl extends RemoteServiceServlet implements ForecastService {

	private String weatherbugEndpoint;
	
	private String weatherbugApikey;

	@Override
	public void init() throws ServletException {
		super.init();
		weatherbugEndpoint = getInitParameter("weatherbug.endpoint");
		weatherbugApikey = getInitParameter("weatherbug.apikey");
	}

	@Override
	public Weather getForecast(double lat, double lng) throws ForecastException {
		try {
			return doGetForecast(lat, lng);
		} catch (IOException e) {
			throw new ForecastException(e);
		}
	}
	
	private Weather doGetForecast(double lat, double lng) throws ForecastException, IOException {
		// create WeatherBug Forecast service URL
		StringBuilder buf = new StringBuilder(weatherbugEndpoint);
		buf.append("?api_key=");
		buf.append(URLEncoder.encode(weatherbugApikey, "UTF-8"));
		buf.append("&lat=").append(lat);
		buf.append("&long=").append(lng);
		buf.append("&OutputType=1");
		URL url = new URL(buf.toString());
		
		// call Active Search
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_OK)
			throw new ForecastException(rc, conn.getResponseMessage());
		
		// parse response
		InputStream in = conn.getInputStream();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		return JAXB.unmarshal(ir, Weather.class);
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
