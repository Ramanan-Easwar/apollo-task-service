package com.skarva.todoservice.service.task;

import com.skarva.todoservice.common.DBConnection;
import com.skarva.todoservice.common.QueryHelper;
import com.skarva.todoservice.model.Task;
import com.skarva.todoservice.model.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDBDAO {
    DBConnection dbConnection;

    @Autowired
    public TaskDBDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Task createTask(Task task) {
        String CREATE_TASK = "INSERT INTO task (task_name, task_status, created, type) " +
                "VALUES(?,?,?,?)" +
                "RETURNING task_id;";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_TASK);
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskStatus());
            preparedStatement.setTimestamp(3, task.getCreated());
            preparedStatement.setString(4, task.getType());
            ResultSet rs = QueryHelper.executeQuery(preparedStatement);
            System.out.println(rs);
            if(rs.next()) {
                return new Task.Builder().setTaskId(rs.getLong(1))
                        .setTaskName(task.getTaskName())
                        .setCreated(task.getCreated())
                        .setTaskStatus(task.getTaskStatus()).build();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error while writing to db: " + e);
        }
        finally {
            QueryHelper.closeConnection(connection);
            QueryHelper.closePreparedStatement(preparedStatement);
        }
    }

    public List<Task> returnAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String GET_ALL_TASKS = "SELECT task_id, task_name, task_status, created, updated, type FROM task;";
        ResultSet rs;
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TASKS)) {
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                tasks.add(new Task.Builder().setTaskId(rs.getLong(1))
                        .setTaskName(rs.getString(2))
                        .setTaskStatus(rs.getString(3))
                        .setCreated(rs.getTimestamp(4))
                        .setUpdated(rs.getTimestamp(5))
                        .setType(rs.getString(6))
                        .build());
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> returnAllTasksWithCondition(String type) {
        List<Task> tasks = new ArrayList<>();
        String GET_TASKS = "SELECT task_id, task_name, task_status, created, updated, type FROM task " +
                "WHERE type = ?;";
        ResultSet rs;
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_TASKS)) {
            preparedStatement.setString(1, type);
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                tasks.add(new Task.Builder().setTaskId(rs.getLong(1))
                        .setTaskName(rs.getString(2))
                        .setTaskStatus(rs.getString(3))
                        .setCreated(rs.getTimestamp(4))
                        .setUpdated(rs.getTimestamp(5))
                        .setType(rs.getString(6))
                        .build());
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task returnTask(Long id) {
        String GET_TASK = "SELECT task_id, task_name, task_status, created, updated, type FROM task WHERE task_id = ?;";
        ResultSet rs;
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_TASK)) {
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Task.Builder().setTaskId(rs.getLong(1))
                        .setTaskName(rs.getString(2))
                        .setTaskStatus(rs.getString(3))
                        .setCreated(rs.getTimestamp(4))
                        .setUpdated(rs.getTimestamp(5))
                        .setType(rs.getString(6))
                        .build();
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String completeTask(Long id) {
        String UPDATE_TASK = "UPDATE task SET task_status=?, type=?,updated=? where task_id=? " +
                "RETURNING task_name;";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_TASK);
            preparedStatement.setString(1, "This task is done");
            preparedStatement.setString(2, TaskType.COMPLETE.type);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(4, id);
            ResultSet rs = QueryHelper.executeQuery(preparedStatement);
            System.out.println(rs);
            if(rs.next()) {
                System.out.println("updated: " + rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error while writing to db: " + e);
        }
        finally {
            QueryHelper.closeConnection(connection);
            QueryHelper.closePreparedStatement(preparedStatement);
        }
    }

}
