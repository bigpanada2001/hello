package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ProxyObject implements InvocationHandler{
    private Object proxied = null;
    public ProxyObject(){
        
    }
    public ProxyObject(Object proxied){
        this.proxied  = proxied;
    }
    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
        System.out.println("hello");
        if(arg0 == this) {
        	System.out.println("-----equal");
        } else {
        	System.out.println("-----not equal");
        }
        return arg1.invoke(proxied, arg2);
    };
}
