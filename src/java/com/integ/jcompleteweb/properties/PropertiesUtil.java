/*
 * To change this license header, choose License Headers in Project PropertiesUtil.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.properties;

import com.integ.jcompleteweb.exception.ApplicationException;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author manan
 */
public class PropertiesUtil {
    
    protected static Properties properties=new Properties();

    public static void loadProperties() throws ApplicationException {
        try {
            String propertiesFile = System.getProperty("properties.file");
            FileInputStream inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
        } catch (Exception ex) {
            throw new ApplicationException("Exception occurred while loading properties file", ex);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}