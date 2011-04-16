package com.mashery.examples.api.server.quova;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mashery.examples.api.client.quova.IPInfo;
import com.mashery.examples.api.client.quova.IPInfoException;
import com.mashery.examples.api.client.quova.IPInfoService;

@SuppressWarnings("serial")
public class IPInfoServiceImpl extends RemoteServiceServlet implements IPInfoService {
	
	private String quovaEndpoint;
	
	private String quovaApikey;
	
	private String quovaSecret;

	@Override
	public void init() throws ServletException {
		super.init();
		quovaEndpoint = getInitParameter("quova.endpoint");
		quovaApikey = getInitParameter("quova.apikey");
		quovaSecret = getInitParameter("quova.secret");
	}

	@Override
	public IPInfo getIPInfo(String ip) throws IPInfoException {
		if (ip == null) {
			// determine caller's IP address
			HttpServletRequest req = getThreadLocalRequest();
			ip = req.getRemoteAddr();
			String forwardedFor = req.getHeader("X-Forwarded-For");
			if (forwardedFor != null && !(forwardedFor = forwardedFor.trim()).isEmpty()) {
				int comma = forwardedFor.indexOf(',');
				if (comma != -1)
					forwardedFor = forwardedFor.substring(0, comma).trim();
				
				if (!forwardedFor.isEmpty())
					ip = forwardedFor;
			}
		}
		
		// compute signature
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IPInfoException(e);
		}
		
		long ts = System.currentTimeMillis() / 1000L;
		String input = quovaApikey + quovaSecret + ts;
		md.update(input.getBytes());
		String sig = String.format("%02x", new BigInteger(1, md.digest()));
		
		try {
			return getIPInfo(ip, sig);
		} catch (IOException e) {
			throw new IPInfoException(e);
		}
	}
	
	private IPInfo getIPInfo(String ip, String sig) throws IPInfoException, IOException {
		// create Quova service URL
		StringBuilder buf = new StringBuilder(quovaEndpoint);
		buf.append(ip).append("?format=flatxml&apikey=");
		buf.append(URLEncoder.encode(quovaApikey, "UTF-8"));
		buf.append("&sig=").append(URLEncoder.encode(sig, "UTF-8"));
		URL url = new URL(buf.toString());
		
		// call Quova
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		int rc = conn.getResponseCode();
		String charset = getContentCharset(conn);
		if (rc != HttpURLConnection.HTTP_OK)
			throw new IPInfoException(rc, conn.getResponseMessage());
		
		// parse response
		InputStream in = conn.getInputStream();
		InputStreamReader ir = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);
		return JAXB.unmarshal(ir, IPInfo.class);
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
