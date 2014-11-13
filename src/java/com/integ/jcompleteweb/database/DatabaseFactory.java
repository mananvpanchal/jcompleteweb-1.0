/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.database;

/**
 *
 * @author manan
 */
public class DatabaseFactory {
    
    protected static DatabaseFactory factory;
    
    public static synchronized DatabaseFactory getInstance() {
        if(factory==null) {
            factory=new DatabaseFactory();
        }
        return factory;
    }
    
    public Database createDatabase() {
        return new Database();
    }
}
