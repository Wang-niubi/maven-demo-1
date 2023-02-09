package com.tecwang.springdemo.lazybean;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Service1{

    String name ;
    Service2 service2;

    public void setService2(Service2 service2){
        System.out.println("注入service2");
        this.service2 = service2;
    }
    

    public static void main(String[] args) {
        // 测试 spring bean标签和容器


        System.out.println();
        String xmlPath = "classpath:com/tecwang/springdemo/lazybean/*.xml";
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 测试获取相同的bean，然后修改的时候会不会同步到容器中
        // Service1 service2 = (Service1)(classPathXmlApplicationContext.getBean("serviceA")); 注释掉之后不会立刻初始化serviceA
        // 将init-lazy设置为false

        
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