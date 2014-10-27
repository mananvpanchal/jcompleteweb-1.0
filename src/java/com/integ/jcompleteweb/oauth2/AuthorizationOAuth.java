/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth2;

import com.integ.jcompleteweb.model.JWToken;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author manan
 */
public abstract class AuthorizationOAuth extends OAuth {

    @Override
    protected void setUpKeys() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.genKeyPair();
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubSpec = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec priSpec = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        savePublicKey(publicKey);
        savePrivateKey(privateKey);
    }
    
    protected abstract void savePrivateKey(RSAPrivateKey privateKey) throws IOException;
    
    protected abstract void savePublicKey(RSAPublicKey publicKey) throws IOException;
    
    private JWToken encryptToken(JWToken token) throws Exception {
        token.setUsername(encryptData(token.getUsername()));
        token.setUserrole(encryptData(token.getUserrole()));
        token.setAccessToken(encryptData(token.getAccessToken()));
        return token;
    }

    public JWToken generateJWToken(String username, String userrole) throws Exception {
        JWToken token = new JWToken();
        token.setUsername(username);
        token.setUserrole(userrole);
        String accessToken = UUID.randomUUID().toString();
        token.setAccessToken(accessToken);
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        token.setExpirationTime(cal.getTime());
        saveJWToken(token.getUsername(), token);
        return encryptToken(token);
    }

    protected abstract void saveJWToken(String username, JWToken token) throws Exception;
    
}
