package com.skarva.todoservice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DBConnection {

    Logger LOG = LoggerFactory.getLogger(DBConnection.class);
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skarva",
                    "skarva", "12345");
            LOG.info("weeeeeeeeeeeeeeeeeeeeeeeeeeeeee db call!!!! {}", connection.getClientInfo());
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to db!!:" + e);
        }
    }
}
