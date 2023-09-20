package com.skarva.todoservice.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
@Setter
public class DbConfig {

    @Value("${db.user}")
    String username;

    @Value("${db.password}")
    String password;

    @Value("${db.port}")
    String port;

    @Value("${db.env}")
    String env;
}
