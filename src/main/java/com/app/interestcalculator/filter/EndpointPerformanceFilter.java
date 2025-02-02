package com.app.interestcalculator.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class EndpointPerformanceFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(EndpointPerformanceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = UrlUtils.buildRequestUrl(request);
        StopWatch watch = StopWatch.createStarted();
        try {
            filterChain.doFilter(request, response);
        } finally {
            watch.stop();
            logger.info("Request URL:[{}], Time taken to serve:[{}]", url, watch.getTime());
        }
    }
}
