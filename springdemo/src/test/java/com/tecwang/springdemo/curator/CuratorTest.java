package com.tecwang.springdemo.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.annotation.security.RunAs;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@SpringBootTest
public class CuratorTest {
     class ZookeeperLock implements Runnable {

        @Override
        public void run() {
            String basePath = "/lock";
            InterProcessReadWriteLock interProcessLock = new InterProcessReadWriteLock(curatorFramework, basePath);
            InterProcessMutex interProcessMutex = interProcessLock.readLock();
            try {
                // 获取锁，没有获取到则阻塞
//                Collection<String> participantNodes = interProcessMutex.getParticipantNodes();
                interProcessMutex.acquire();
                for (int i = 0; i <1000; i++) {
                    Thread.sleep(1000);
                    System.out.println(i);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Autowired
    CuratorFramework curatorFramework;
    @Test
    void createNode() throws Exception {
        curatorFramework.create().forPath("/asd");


    }
    @Test
    void listenNode() throws Exception {
        String nodePath = "/listen/test";
        NodeCache nodeCache = new NodeCache(curatorFramework, nodePath);
        nodeCache.getListenable().addListener(() -> {
            log.info("节点发生变化");
            byte[] bytes = curatorFramework.getData().forPath(nodePath);
            System.out.println(new String(bytes));
        });
        nodeCache.start();

        System.in.read();

    }
    @Test
    void lockRead1() throws Exception {
        String basePath = "/asdasdasda";
        InterProcessReadWriteLock interProcessLock = new InterProcessReadWriteLock(curatorFramework, basePath);
        InterProcessMutex interProcessMutex = interProcessLock.readLock();
        try {
            // 获取锁，没有获取到则阻塞
//                Collection<String> participantNodes = interProcessMutex.getParticipantNodes();
            interProcessMutex.acquire();
            for (int i = 0; i <1000; i++) {
                Thread.sleep(1000);
                System.out.println(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void lockRead2() throws Exception {
        String basePath = "/asdasdasda";
        InterProcessReadWriteLock interProcessLock = new InterProcessReadWriteLock(curatorFramework, basePath);
        InterProcessMutex interProcessMutex = interProcessLock.readLock();
        try {
            // 获取锁，没有获取到则阻塞
//                Collection<String> participantNodes = interProcessMutex.getParticipantNodes();
            interProcessMutex.acquire();
            for (int i = 0; i <1000; i++) {
                Thread.sleep(1000);
                System.out.println(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void lockWrite() throws Exception {

        String basePath = "/asdasdasda";
        InterProcessReadWriteLock interProcessLock = new InterProcessReadWriteLock(curatorFramework, basePath);
        // 想要获取写锁，这个节点必须没有任何的锁，获取锁其实就是在这个节点下面创建子节点，然后子节点的内容是创建这个节点的ip

        // 由于这一把锁的存在，所有人想要进行操作都要通过这一把锁这一关。相比于锁我感觉更像是钥匙
        InterProcessMutex interProcessMutex = interProcessLock.writeLock();
        try {
            // 获取锁，没有获取到则阻塞
//                Collection<String> participantNodes = interProcessMutex.getParticipantNodes();
            // 上锁
            // 下面的代码有个缺陷，如果谁在循环中将锁删除了，那这其实是不知道的。锁本质上就是一个节点。
            interProcessMutex.acquire();
            // 写数据
            for (int i = 0; i <1000; i++) {
                Thread.sleep(1000);
                System.out.println(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}

