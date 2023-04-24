package com.tecwang.springdemo.dev.demo;

import com.tecwangdemo.dao.T1;
import com.tecwangdemo.mapper.T1Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@SpringBootTest
public class DaoTest {
    @Autowired
    private T1Mapper t1Mapper;

    @Test
    public void selectTest() {
        T1 t1 = t1Mapper.selectById(13);
        System.out.println(t1);
    }

    @Test
    public void insertTest() {
        long timestamp = 1682273037000L; // 东八区1:40
        T1 t1 = new T1();
        t1.setId(13L);
        t1.setTime(new Time(timestamp));
        t1.setDateTime(new Date(System.currentTimeMillis()));
        t1.setTimestamp(new Timestamp(timestamp));
        int insert = t1Mapper.insert(t1);
        System.out.println(insert);
    }
}
