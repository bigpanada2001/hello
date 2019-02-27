package com.proxy;

class RealObject implements ProxyInterface{
    //实现接口方法
    @Override
    public void say() {
        // TODO Auto-generated method stub
        System.out.println("say");
    }
    
}

