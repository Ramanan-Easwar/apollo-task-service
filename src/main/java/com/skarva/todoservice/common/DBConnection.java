package com.skarva.todoservice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DBConnection {
    DbConfig dbConfig;

    @Autowired
    public DBConnection(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
    Logger LOG = LoggerFactory.getLogger(DBConnection.class);
    public Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:postgresql://" + dbConfig.getEnv() + ":" + dbConfig.getPort() +
                    "/" + dbConfig.getUsername();
            connection = DriverManager.getConnection(url,
                    dbConfig.getUsername(), dbConfig.getPassword());
            LOG.info("db call!!!! {}", connection.getClientInfo());
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to db!!:" + e);
        }
    }
}
