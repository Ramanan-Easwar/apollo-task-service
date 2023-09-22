package com.skarva.todoservice.common;

import com.fasterxml.uuid.Generators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class QueryHelper {

    public static ResultSet executeQuery(PreparedStatement preparedStatement) {
        try {
            boolean execute = preparedStatement.execute();
            if(execute)
                return preparedStatement.getResultSet();
            else
                throw new RuntimeException("Query was not executed!!!");
        } catch (Exception e) {
            throw new RuntimeException("Exception thrown while executing!: " + e);
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                throw new RuntimeException("Could not close statement: " + e);
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                throw new RuntimeException("Could not close connection: " + e);
            }
        }
    }

    public static String generateUUID(String toUUIString) {
        UUID uuid = Generators.nameBasedGenerator().generate(toUUIString);
        return uuid.toString();
    }
}
