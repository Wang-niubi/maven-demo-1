package com.tecwangdemo.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class T1 {
    //todo JAVA时间类型了解学习
    private Long id;
    @TableField("datetime")
    private Date dateTime;
    private Time time;
    private Timestamp timestamp;
}
