package com.app.interestcalculator.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;

import java.io.IOException;

public class ApiPerformanceMetrics implements ClientHttpRequestInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(ApiPerformanceMetrics.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            ClientHttpResponse response = execution.execute(request, body);
            stopWatch.stop();
            logPerformanceMetrics(request.getURI().toString(), true, stopWatch.lastTaskInfo().getTimeMillis());
            return response;
        } catch (Exception e) {
            if(stopWatch.isRunning()){
                stopWatch.stop();
            }
            logPerformanceMetrics(request.getURI().toString(), false, stopWatch.lastTaskInfo().getTimeMillis());
            throw e;
        }

    }

    private void logPerformanceMetrics(String message, boolean success, long timeTaken) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ApiPerformanceMetrics.class.getName()).append(" ");
        buffer.append("'").append(message).append("' ");
        buffer.append(StringUtils.EMPTY).append(" ");
        buffer.append(success).append(" ");
        buffer.append(timeTaken);
        logger.info(buffer.toString());

    }


}
