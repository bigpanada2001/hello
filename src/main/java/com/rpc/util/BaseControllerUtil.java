package com.rpc.util;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;




public class BaseControllerUtil {
	public static <T> void writeResponse(HttpServletRequest request,
			HttpServletResponse response, Object rst)
			throws IOException {
		setResponseHeader(request, response);
		JsonUtil.writeValue(response.getOutputStream(), rst);
	}
	
	public static <T> void setResponseHeader(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/plain; charset=UTF-8");
		// String acao = "http://static.shs2.stat.game.yy.com";
		// if(Constant.isTestDwEnv()){
		// acao = "http://static.shs2.stat.game.yy.com:2001" ;
		// }
		String refer = request.getHeader("Referer");
		if (!StringUtils.isEmpty(refer)) {
			URL url = new URL(refer);
			refer = url.getProtocol() + "://" + url.getHost();
			if (url.getPort() != -1) {
				refer += ":" + url.getPort();
			}
			response.setHeader("Access-Control-Allow-Origin", refer);
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}

	}
}
