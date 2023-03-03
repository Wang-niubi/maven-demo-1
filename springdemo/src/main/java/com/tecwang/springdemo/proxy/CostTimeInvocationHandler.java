package com.tecwang.springdemo.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理方法时间统计类
 */
public class CostTimeInvocationHandler implements InvocationHandler {
    private Object target;

    public CostTimeInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();

        // 传入需要调用的方法实例和参数
        Object result = method.invoke(target, args);

        long endTime = System.nanoTime();
        System.out.println(method.getName() + "方法用时:" + (endTime - startTime));
        return result;
    }


    /**
     * 静态创建代理类的方法
     * 使用了 JDK 动态代理，就是使用动态代理代理接口使用字节码生成代理类。传入 target 进行 invo
     * @return
     * @param <T>
     */
    public static <T> T createProxy(Object target , Class<T> targetInterface) throws Exception {
        // JDK 动态代理只可以代理接口
        // 对接口进行判断
//        if (!targetInterface.isInterface()) {
//            throw new Exception("JDK 动态代理只可以代理接口");
//        } else if (!targetInterface.isAssignableFrom(target.getClass())) {
//            // 判断target 是否实现了targetInterface
//            throw new Exception("target 没有实现对应接口");
//        }

                if (!targetInterface.isInterface()) {
            throw new Exception("JDK 动态代理只可以代理接口");
        }
        // 创建实例
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new CostTimeInvocationHandler(target));

    }

    public static class TestTimeClass {
        public void run() {
            for (int i = 0; i < 100; i++) {
                i *= i;
            }
        }

    }

    @Test
    public void testTime() throws Exception{
        Object proxy = CostTimeInvocationHandler.createProxy(new TestTimeClass(),IService.class);

    }
}
