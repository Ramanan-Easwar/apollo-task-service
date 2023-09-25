package com.skarva.todoservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class UserFilter extends OncePerRequestFilter {


    Logger logger = LoggerFactory.getLogger(UserFilter.class);
    private static final String AUTH_KEY = "authorization";
    String authKey;

    public UserFilter(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                 FilterChain filterChain)
            throws IOException, ServletException {
       logger.info("Inside the request auth!!!");

        Collections.list(servletRequest.getHeaderNames()).forEach(
                header -> {
                    logger.info("header-key:val- {}:{} ", header, servletRequest.getHeader(header));
                }
        );

        String key = servletRequest.getHeader(AUTH_KEY).split(" ")[1];
        if(key.equals(authKey))
            filterChain.doFilter(servletRequest, servletResponse);
        else {
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to authenticate");
        }

    }


}
