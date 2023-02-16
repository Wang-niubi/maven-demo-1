package com.tecwang.springdemo.proxy;

import org.springframework.cglib.proxy.Proxy;

public interface IService {
    void m1();
    void m2();
    void m3();

    public static void main(String[] args) {
        Class clazz = Proxy.getProxyClass(IService.class.getClassLoader(), IService.class);
        
    }
}



