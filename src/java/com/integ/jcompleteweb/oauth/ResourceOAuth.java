/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.model.EncryptedJWToken;
import com.integ.jcompleteweb.model.JWToken;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manan
 */
public abstract class ResourceOAuth extends OAuth {
    
    private static final Logger LOG=Logger.getLogger("JCW_LOGGER");
    protected SimpleDateFormat dateFormat;
    protected Map<String, JWToken> tokenMap=Collections.synchronizedMap(new HashMap<>());
    
    public ResourceOAuth() {
        dateFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    protected void setUpKeys() throws Exception {
        publicKey = readPublicKey("public.key");
        privateKey = readPrivateKey("private.key");
        LOG.log(Level.INFO, "Resource Public Key: {0}", new String(publicKey.getEncoded()));
        LOG.log(Level.INFO, "Resource Private Key: {0}", new String(privateKey.getEncoded()));
        LOG.log(Level.INFO, "Keys set up in ResourceOAuth");
    }

    protected abstract RSAPublicKey readPublicKey(String name) throws Exception;

    protected abstract RSAPrivateKey readPrivateKey(String name) throws Exception;

    private JWToken decryptToken(EncryptedJWToken encryptedToken) throws Exception {
        JWToken token = new JWToken();
        token.setUsername(new String(decryptData(encryptedToken.getUsername())));
        token.setUserrole(new String(decryptData(encryptedToken.getUserrole())));
        token.setAccessToken(new String(decryptData(encryptedToken.getAccessToken())));
        token.setExpirationTime(new String(decryptData(encryptedToken.getExpirationTime())));
        return token;
    }
    
    private EncryptedJWToken decodeToken(JWToken token) throws Exception {
        EncryptedJWToken encryptedToken = new EncryptedJWToken();
        encryptedToken.setUsername(decodeData(token.getUsername().getBytes()));
        encryptedToken.setUserrole(decodeData(token.getUserrole().getBytes()));
        encryptedToken.setAccessToken(decodeData(token.getAccessToken().getBytes()));
        encryptedToken.setExpirationTime(decodeData(token.getExpirationTime().getBytes()));
        return encryptedToken;
    }

    public String validateJWToken(JWToken token) throws Exception {
        LOG.log(Level.INFO, "Token validation");
        token = decryptToken(decodeToken(token));
        try {
            if(tokenMap.get(token.getUsername()) == null) {
                LOG.log(Level.INFO, "Token need to be retrieved for user: {0}", token.getUsername());
                tokenMap.put(token.getUsername(), readJWToken(token.getUsername()));
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception occurred", ex);
            return OAuth.TOKEN_NOT_FOUND;
        }
        if (dateFormat.parse(token.getExpirationTime()).before(new Date())) {
            tokenMap.remove(token.getUsername());
            return OAuth.TOKEN_TIMED_OUT;
        }
        if (token.getAccessToken().equals(tokenMap.get(token.getUsername()).getAccessToken())) {
            return OAuth.TOKEN_SUCCEED;
        } else {
            return OAuth.TOKEN_NOT_MATCHED;
        }
    }

    protected abstract JWToken readJWToken(String username) throws Exception;

}
