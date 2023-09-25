package com.skarva.todoservice.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class FilterConfig {

    @Value("${auth.key}")
    private String authKey;
    @Bean
    public FilterRegistrationBean<UserFilter> registrationBean() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter(authKey));
        registrationBean.setUrlPatterns(Collections.singletonList("/users/*"));
        return registrationBean;
    }
}
