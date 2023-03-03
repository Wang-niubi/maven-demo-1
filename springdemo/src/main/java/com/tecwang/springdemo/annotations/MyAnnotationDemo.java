package com.tecwang.springdemo.annotations;


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;


// Type Parameter 就是泛型的 parameter
@Target({ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann1 {
    // 自定义注解，注解最后面要加上一个()
    String[] value();
}

public class MyAnnotationDemo<@Ann1("T0是在类上声明的一个泛型类型变量") T0> {
    public <@Ann1("T0是在方法上声明的泛型类型变量") T2> void m1() {

    }


    public static void main(String[] args) {

        // 获取m1 方法上的的注解
        for (Method declaredMethod : MyAnnotationDemo.class.getDeclaredMethods()) {
            if (declaredMethod.getName().equals("m1")) {
                TypeVariable<Method>[] typeParameters = declaredMethod.getTypeParameters();
                for (TypeVariable<Method> typeParameter : typeParameters) {
                    // 这个是拿到被注解的参数的名称
                    System.out.println(typeParameter.getName());
                    // 这是按到注解
                    Arrays.stream(typeParameter.getAnnotations()).forEach(System.out::println);
                }
            }
        }


        Package aPackage = Package.getPackage("com.tecwang.springdemp.proxy");
        System.out.println(aPackage);

    }
}