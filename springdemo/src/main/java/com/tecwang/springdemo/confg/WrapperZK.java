package com.tecwang.springdemo.confg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "curator")
@Data
public class WrapperZK {
    private int retryCount ;
    private int elapsedTimeMs ;
    private String connectString ;
    private int sessionTimeoutMs ;
    private int connectionTimeoutMs;
}
