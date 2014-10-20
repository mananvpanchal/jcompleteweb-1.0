/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.model;

import com.sun.xml.messaging.saaj.util.Base64;

/**
 *
 * @author manan
 */
public class JWToken {
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    protected String username;
    protected String userrole;
    protected String accessToken;
    
    @Override
    public String toString() {
        return "{\"accessToken\":\""+accessToken+"\", \"username\":\""+username+"\", \"userrole\":\""+userrole+"\"}";
    }
    
}
