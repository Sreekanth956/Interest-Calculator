package com.app.interestcalculator;

import com.app.interestcalculator.interceptor.ApiPerformanceMetrics;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Configuration
public class AppConfig {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(Collections.singletonList(new ApiPerformanceMetrics()))
                .build();
    }


}
