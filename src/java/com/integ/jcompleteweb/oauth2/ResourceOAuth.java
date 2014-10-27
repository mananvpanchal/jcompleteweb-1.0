/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth2;

import com.integ.jcompleteweb.model.JWToken;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 *
 * @author manan
 */
public abstract class ResourceOAuth extends OAuth {

    @Override
    protected void setUpKeys() throws Exception {
        publicKey=readPublicKey("public.key");
        privateKey=readPrivateKey("private.key");
    }
    
    protected abstract RSAPublicKey readPublicKey(String name) throws Exception;
    
    protected abstract RSAPrivateKey readPrivateKey(String name) throws Exception;
    
    private JWToken decryptToken(JWToken token) throws Exception {
        token.setUsername(decryptData(token.getUsername()));
        token.setUserrole(decryptData(token.getUserrole()));
        token.setAccessToken(decryptData(token.getAccessToken()));
        return token;
    }
    
    public String validateJWToken(JWToken token) throws Exception {
        if(token.getExpirationTime().before(new Date())) {
            return OAuth.TOKEN_TIMED_OUT;
        }
        JWToken retrievedToken = readJWToken(decryptData(token.getUsername()));
        token = decryptToken(token);
        if(token.getAccessToken().equals(retrievedToken.getAccessToken())) {
            return OAuth.TOKEN_SUCCEED;
        } else {
            return OAuth.TOKEN_NOT_MATCHED;
        }
    }
    
    protected abstract JWToken readJWToken(String username) throws Exception;

}
