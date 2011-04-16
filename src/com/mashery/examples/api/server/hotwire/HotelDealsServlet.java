package com.mashery.examples.api.server.hotwire;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.codehaus.jackson.map.ObjectMapper;

import com.mashery.examples.api.client.hotwire.HotwireResponse;

@SuppressWarnings("serial")
public class HotelDealsServlet extends HttpServlet {

	private String hotwireEndpoint;
	
	private String hotwireApikey;

	@Override
	public void init() throws ServletException {
		super.init();
		hotwireEndpoint = getInitParameter("hotwire.endpoint");
		hotwireApikey = getInitParameter("hotwire.apikey");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// create Active Search service URL
		StringBuilder buf = new StringBuilder(hotwireEndpoint);
		buf.append("?apikey=");
		buf.append(URLEncoder.encode(hotwireApikey, "UTF-8"));
		String qs = req.getQueryString();
		if (qs != null)
			buf.append('&').append(qs);
		
		URL url = new URL(buf.toString());
		
		// call Hotwire Hotel Deals
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_OK) {
			resp.sendError(rc, conn.getResponseMessage());
			return;
		}
		
		// parse XML response
		InputStream in = conn.getInputStream();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		HotwireResponse response = JAXB.unmarshal(ir, HotwireResponse.class);
		
		// return JSON response
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getWriter(), response);
		resp.flushBuffer();
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
