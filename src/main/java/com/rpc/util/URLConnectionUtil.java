package com.rpc.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class URLConnectionUtil {
	
	public static String POST = "POST";
	public static String GET = "GET";
	public static String FormEncodedUTF8 = "application/x-www-form-urlencoded;charset=UTF-8";

	/**
	 * 建立POST URL连接
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	public static HttpURLConnection getUrlConnectionPOST(String url) throws IOException, ProtocolException {
		return getUrlConnection(url, POST, FormEncodedUTF8);
	}
	
	/**
	 * 建GET URL立连接，并发送数�?
	 * @param url
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public static String urlGETRequest(String url) throws ProtocolException, IOException {
		HttpURLConnection c = getUrlConnectionGET(url);
		return IOUtils.toString(c.getInputStream());
	}
	
	/**
	 * 建立GET URL连接
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	public static HttpURLConnection getUrlConnectionGET(String url) throws IOException, ProtocolException {
		return getUrlConnection(url, GET, FormEncodedUTF8);
	}
	
	/**
	 * 建立URL连接
	 * @param url
	 * @param requestMethod
	 * @param contenType
	 * @param connectTimeOut
	 * @param readTimeOut
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	public static HttpURLConnection getUrlConnection(String url, String requestMethod, String contenType) throws IOException, ProtocolException {
		HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
		httpUrlConnection.setDoOutput(true);
		httpUrlConnection.setDoInput(true);
		httpUrlConnection.setUseCaches(false);
		httpUrlConnection.setConnectTimeout(1000*60);
		httpUrlConnection.setReadTimeout(1000*60*5);
		httpUrlConnection.setRequestMethod(requestMethod); 
        httpUrlConnection.setRequestProperty("Content-type", contenType);
		httpUrlConnection.connect();
		return httpUrlConnection;
	}
	
}
