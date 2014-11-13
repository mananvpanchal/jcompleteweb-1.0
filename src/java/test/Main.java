/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import com.integ.jcompleteweb.database.Database;
import com.integ.jcompleteweb.database.DatabaseFactory;

/**
 *
 * @author manan
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        Database database = DatabaseFactory.getInstance().createDatabase();
        database.open();
        database.start();
        database.end();
        
        database.close();
        database.commit();
    }
}