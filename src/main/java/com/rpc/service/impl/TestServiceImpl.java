package com.rpc.service.impl;

import com.rpc.service.TestService;

public class TestServiceImpl implements TestService {
	static {
		String a="";
		System.out.println(a);
	}
	public String hello(String name) {
		return "hello:"+name;
	}
}
