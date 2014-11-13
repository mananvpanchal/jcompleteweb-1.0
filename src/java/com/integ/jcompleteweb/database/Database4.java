/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.database;

import com.integ.jcompleteweb.exception.ApplicationException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author manan
 */
public class Database4 {
    
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    
    protected Database4() {
        CallableStatement callableStatement=null;
    }
    
    public Connection open() throws Exception {
        String driverClassFQN = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jcompleteweb";
        String username = "manan";
        String password = "12345678";
        return createConnection(driverClassFQN, url, username, password);
    }
    
    protected Connection createConnection(String driverClassFQN, String url, String username, String password) throws Exception {
        Class.forName(driverClassFQN);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
    
    public Connection start() throws Exception {
        connection.setAutoCommit(false);
        return connection;
    }
    
    public void end(boolean commit) throws Exception {
        end(commit, resultSet);
    }
    
    public void end(boolean commit, ResultSet resultSet) throws Exception {
        if(resultSet !=null && !resultSet.isClosed()) {
            resultSet.close();
        }
        if(statement !=null && !statement.isClosed()) {
            statement.close();
        }
        if(connection != null && !connection.isClosed()) {
            if(commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        }
    }
    
    protected void closeRemainingResource() throws Exception {
        if(resultSet !=null && !resultSet.isClosed()) {
            resultSet.close();
        }
        if(statement !=null && !statement.isClosed()) {
            statement.close();
        }
    }
    
    public void closeResultSet(ResultSet resultSet) throws Exception {
        if(resultSet !=null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }
    
    public void closeStatement(Statement statement) throws Exception {
        if(statement !=null && !statement.isClosed()) {
            statement.close();
        }
    }
    
    public void close() throws Exception {
        closeRemainingResource();
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    protected void closeCurrentStatement() throws Exception {
        if(statement!=null && !statement.isClosed()) {
            statement.close();
        }
    }
    
    public Statement createStatement() throws Exception {
        closeCurrentStatement();
        statement=connection.createStatement();
        return statement;
    }
    
    public PreparedStatement createPreparedStatement(String sql) throws Exception {
        closeCurrentStatement();
        statement = connection.prepareStatement(sql);
        return (PreparedStatement)statement;
    }
    
    public CallableStatement createCallableStatement(String sql) throws Exception {
        closeCurrentStatement();
        statement=connection.prepareCall(sql);
        return (CallableStatement)statement;
    }
    
    public ResultSet executeQuery(String sql) throws Exception {
        createStatement();
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }
    
    public ResultSet executeQuery() throws Exception {
        resultSet = ((PreparedStatement)statement).executeQuery();
        return resultSet;
    }
}
