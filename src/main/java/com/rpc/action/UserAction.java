package com.rpc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rpc.util.BaseControllerUtil;


@Controller
public class UserAction {
	
	private static Logger LOG = (Logger)LoggerFactory.getLogger(UserAction.class);
	
	@Autowired
	com.yy.selfservice.webservice.impl.TestService testServiceClient;
	@RequestMapping("test")
	public void test(ModelMap model, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		String rst = testServiceClient.hello(name);
		BaseControllerUtil.writeResponse(request, response, rst);
	}
}
