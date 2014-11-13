/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.database;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author manan
 */
public interface Executor {
    
    ResultSet execute(Statement statement, Object... resources);
    
}
