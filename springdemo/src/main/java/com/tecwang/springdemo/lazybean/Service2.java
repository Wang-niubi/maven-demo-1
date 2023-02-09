
package com.tecwang.springdemo.lazybean;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Service2{

    String name ;
    
    public Service2(){
        System.out.println("懒加载");
        System.out.println(this);

    }

    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "name"+":"+name;
    }
}