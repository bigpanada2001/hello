package com.proxy;

import java.lang.reflect.*;

public class Main {
    static void customer(ProxyInterface pi){
        pi.say();
    }
    public static void main(String[] args){
        RealObject real = new RealObject();
        ProxyInterface proxy = (ProxyInterface)Proxy.newProxyInstance(ProxyInterface.class.getClassLoader(),new Class[]{ProxyInterface.class}, new ProxyObject(real));
        customer(proxy);
    }
}