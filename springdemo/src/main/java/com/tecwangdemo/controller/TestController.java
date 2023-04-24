package com.tecwangdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("test")
public class TestController {
    @GetMapping("")
    public String test() {
        // 数据库查询
        return null;
    }
}
