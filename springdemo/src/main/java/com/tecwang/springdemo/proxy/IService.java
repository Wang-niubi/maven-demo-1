package com.tecwang.springdemo.proxy;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface IService {
    void m1();

    void m2();

    void m3();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException {

    }


    public static class A{
        public static void main(String[] args) throws Exception {
            // 代理类，这个方法是获取类
            @SuppressWarnings("unchecked")
            Class<IService> proxyClass =(Class<IService>) (Proxy.getProxyClass(IService.class.getClassLoader(), IService.class));
            // 给代理类代理传递参数和方法调用设置，也就是代理类的处理器
            InvocationHandler invocationHandler = new InvocationHandler() {

                /**
                 * 被代理的方法被调用的时候真正执行的方法的方法。也就是代理方法
                 * @param proxy the proxy instance that the method was invoked on
                 *
                 *
                 * @param method the {@code Method} instance corresponding to
                 * the interface method invoked on the proxy instance.  The declaring
                 * class of the {@code Method} object will be the interface that
                 * the method was declared in, which may be a superinterface of the
                 * proxy interface that the proxy class inherits the method through.
                 *
                 * @param args an array of objects containing the values of the
                 * arguments passed in the method invocation on the proxy instance,
                 * or {@code null} if interface method takes no arguments.
                 * Arguments of primitive types are wrapped in instances of the
                 * appropriate primitive wrapper class, such as
                 * {@code java.lang.Integer} or {@code java.lang.Boolean}.
                 *
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理了" + method.getName());
                    return null;
                }
            };

            //创建实例，这个实例
            IService iService = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
            iService.m1();
            iService.m2();
            iService.m3();


            System.out.println("------------");
            IService o =(IService) Proxy.newProxyInstance(IService.class.getClassLoader(), new Class[]{IService.class}, invocationHandler);
            o.m1();
            o.m2();
            o.m3();
        }


    }
}

