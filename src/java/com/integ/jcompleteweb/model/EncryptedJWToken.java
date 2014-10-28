/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.model;

import java.io.Serializable;

/**
 *
 * @author manan
 */
public class EncryptedJWToken implements Serializable {

    public byte[] getUsername() {
        return username;
    }

    public void setUsername(byte[] username) {
        this.username = username;
    }

    public byte[] getUserrole() {
        return userrole;
    }

    public void setUserrole(byte[] userrole) {
        this.userrole = userrole;
    }

    public byte[] getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(byte[] accessToken) {
        this.accessToken = accessToken;
    }

    public byte[] getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(byte[] expirationTime) {
        this.expirationTime = expirationTime;
    }
    
    protected byte[] username;
    protected byte[] userrole;
    protected byte[] accessToken;
    protected byte[] expirationTime;
    
}
