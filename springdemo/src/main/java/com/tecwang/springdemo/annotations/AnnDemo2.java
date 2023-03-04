package com.tecwang.springdemo.annotations;



import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

@Target({ElementType.PACKAGE,
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.CONSTRUCTOR,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.TYPE_PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann11 {
    String value();
}

@Target({ElementType.PACKAGE,
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.CONSTRUCTOR,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.TYPE_PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface Ann11_0 {
    int value();
}


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Ann12{
    Ann11 [] value();
}

@Ann11("用在了类上")
@Ann11_0(0)
public class AnnDemo2<@Ann11("用在了类变量类型V1上") @Ann11_0(1) V1, @Ann11("用在了类变量类型V2上") @Ann11_0(2) V2> {
    @Ann11("用在了字段上")
    @Ann11_0(3)
    private String name;

    private Map<@Ann11("用在了泛型类型上,String") @Ann11_0(4) String, @Ann11("用在了泛型类型上,Integer") @Ann11_0(5) Integer> map;

    @Ann11("用在了构造方法上")
    @Ann11_0(6)
    public AnnDemo2() {
        this.name = name;
    }

    @Ann11("用在了返回值上")
    @Ann11_0(7)
    public String m1(@Ann11("用在了参数上") @Ann11_0(8) String name) {
        return null;
    }


    @Test
    public void m4() throws NoSuchFieldException, ClassNotFoundException {
        // 首先获取 field
        Field field = AnnDemo2.class.getDeclaredField("map");
        // 然后获取 field 中真实的泛型类型
        ParameterizedType genericType = (ParameterizedType)field.getGenericType();
        // 通过泛型去获取真实的泛型类型
        Type[] actualTypeArguments =  genericType.getActualTypeArguments();

        // 获取这个 field 中所有的注解
        AnnotatedType annotatedType = field.getAnnotatedType();
        AnnotatedType[] annotatedActualTypeArguments = ((AnnotatedParameterizedType) annotatedType).getAnnotatedActualTypeArguments();
        int i = 0;
        for (AnnotatedType actualTypeArgument : annotatedActualTypeArguments) {
            Type actualTypeArgument1 = actualTypeArguments[i++];
            System.out.println(actualTypeArgument1.getTypeName() + "类型上的注解如下：");
            for (Annotation annotation : actualTypeArgument.getAnnotations()) {
                System.out.println(annotation);
            }
        }
    }
}