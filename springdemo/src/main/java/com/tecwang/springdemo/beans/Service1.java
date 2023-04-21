package com.tecwang.springdemo.beans;

//import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Service1{

    String name ;


    public static void main(String[] args) {
        // 测试 spring bean标签和容器
        String xmlPath = "classpath:*.xml";
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 测试获取相同的bean，然后修改的时候会不会同步到容器中
        Service1 service1 = (Service1)(classPathXmlApplicationContext.getBean("serviceA"));
        Service1 service2 = (Service1)(classPathXmlApplicationContext.getBean("serviceA"));

        System.out.println( service1.getName());

        service1.setName("小红");
        System.out.println( service2.getName());
        // 最终输出
        //xiaoming  小红，在单例的情况下你的 bean 一定不能随便进行修改。


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