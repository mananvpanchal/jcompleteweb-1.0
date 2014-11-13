/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author manan
 */
public class Database2 {
    
    protected Executor executor;
    
    public static String STATEMENT = "STATEMENT";
    public static String PREPARED_STATEMENT = "PREPARED_STATEMENT";
    public static String CALLABLE_STATEMENT = "CALLABLE_STATEMENT";

    public Database2(Executor executor) {
        this.executor = executor;
    }

    public void execute(String statementType) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String driverClassFQN = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/jcompleteweb";
            String username = "manan";
            String password = "12345678";
            Class.forName(driverClassFQN);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            if(statementType.equals(STATEMENT)) {
                statement=connection.createStatement();
                resultSet=executor.execute(statement);
            }
        } finally {
            if(resultSet!=null) {
                resultSet.close();
            }
            if(statement!=null) {
                statement.close();
            }
            if(connection!=null) {
                connection.commit();
                connection.close();
            }
        }
    }
}
