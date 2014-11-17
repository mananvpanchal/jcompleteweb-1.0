/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.database;

import com.integ.jcompleteweb.exception.ApplicationException;
import com.integ.jcompleteweb.properties.PropertiesUtil;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author manan
 */
public class Database {
    
    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    protected boolean[] state = new boolean[5];

    protected Database() {
        LOG.log(Level.INFO, "constructor");
        setState(false, false, true, true, true);
    }

    protected final void setState(boolean opened, boolean started, boolean ended, boolean committed, boolean closed) {
        state[0] = opened;
        state[1] = started;
        state[2] = ended;
        state[3] = committed;
        state[4] = closed;
    }

    protected boolean isOpened() {
        return state[0];
    }

    protected boolean isStarted() {
        return state[1];
    }

    protected boolean isEnded() {
        return state[2];
    }

    protected boolean isCommitted() {
        return state[3];
    }

    protected boolean isClosed() {
        return state[4];
    }

    public void open() throws Exception {
        LOG.log(Level.INFO, "open");
        if(!isEnded()) {
            throw new ApplicationException("end() was not called after previous start()");
        }
        if(!isCommitted()) {
            throw new ApplicationException("commit() or rollback() was not called after previous open()");
        }
        if (!isClosed()) {
            throw new ApplicationException("close() was not called after previous open()");
        }
        InitialContext context=new InitialContext();
        DataSource dataSource=(DataSource)context.lookup(PropertiesUtil.getProperty("db_datasource"));
        LOG.log(Level.INFO, PropertiesUtil.getProperty("db_datasource"));
        /*String driverClassFQN = PropertiesUtil.getProperty("db_driver");
        String url = PropertiesUtil.getProperty("db_url");
        String username = PropertiesUtil.getProperty("db_username");
        String password = PropertiesUtil.getProperty("db_password");
        Class.forName(driverClassFQN);
        connection = DriverManager.getConnection(url, username, password);*/
        connection=dataSource.getConnection();
        connection.setAutoCommit(false);
        setState(true, false, false, false, false);
    }

    public void start() throws Exception {
        LOG.log(Level.INFO, "start");
        if (!isOpened()) {
            throw new ApplicationException("open() hould be called before calling start()");
        }
        setState(true, true, false, false, false);
    }

    public void end() throws Exception {
        LOG.log(Level.INFO, "end");
        if (!isStarted()) {
            throw new ApplicationException("start() should be called before calling end()");
        }
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        setState(true, false, true, false, false);
    }

    public void commit() throws Exception {
        LOG.log(Level.INFO, "commit");
        if (!isOpened()) {
            throw new ApplicationException("open() hould be called before calling commit() or rollback()");
        }
        if (!isEnded()) {
            throw new ApplicationException("end() should be called before calling commit() or rollback()");
        }
        connection.commit();
        setState(true, false, true, true, false);
    }

    public void rollback() throws Exception {
        LOG.log(Level.INFO, "rollback");
        if (!isEnded()) {
            throw new ApplicationException("end() should be called before calling commit() or rollback()");
        }
        connection.rollback();
        setState(true, false, true, true, false);
    }

    public void close() throws Exception {
        LOG.log(Level.INFO, "close");
        if(!isEnded()) {
            end();
        }
        if (!isCommitted()) {
            rollback();
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
        setState(false, false, true, true, true);
    }

    public ResultSet executeQuery(String sql) throws Exception {
        LOG.log(Level.INFO, "Executing sql {0}", sql);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }
    
    public ResultSet executePreparedQuery(String sql, Object[] values) throws Exception {
        LOG.log(Level.INFO, "Executing sql {0}", sql);
        statement = connection.prepareStatement(sql);
        for(int i=1;i<=values.length;i++) {
            setParameter(i, values[i-1]);
        }
        resultSet = ((PreparedStatement)statement).executeQuery();
        return resultSet;
    }
    
    protected void setParameter(int index, Object value) throws Exception {
        LOG.log(Level.INFO, "Setting parameter \"{0}\" to index \"{1}\"", new Object[]{value, index});
        PreparedStatement preparedStatement= (PreparedStatement)this.statement;
        if(value instanceof InputStream) {
            preparedStatement.setBinaryStream(index, (InputStream)value);
        } else if(value instanceof String) {
            preparedStatement.setString(index, (String)value);
        } else if(value instanceof Date) {
            preparedStatement.setTimestamp(index, new Timestamp(((Date)value).getTime()));
        } else if(value instanceof Integer) {
            preparedStatement.setInt(index, (Integer)value);
        } else if(value instanceof Long) {
            preparedStatement.setLong(index, (Long)value);
        } else if(value instanceof Float) {
            preparedStatement.setFloat(index, (Float)value);
        } else if(value instanceof Double) {
            preparedStatement.setDouble(index, (Double)value);
        }
    }
    
    public int executeUpdate(String sql) throws Exception {
        statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }
    
    public int executePreparedUpdate(String sql, Object[] values) throws Exception {
        statement = connection.prepareStatement(sql);
        for(int i=1;i<=values.length;i++) {
            setParameter(i, values[i-1]);
        }
        return ((PreparedStatement)statement).executeUpdate();
    }

}
