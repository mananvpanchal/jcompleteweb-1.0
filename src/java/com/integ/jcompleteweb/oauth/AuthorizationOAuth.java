/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.model.EncryptedJWToken;
import com.integ.jcompleteweb.model.JWToken;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manan
 */
public abstract class AuthorizationOAuth extends OAuth {

    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");
    protected Map<String, JWToken> tokenMap = Collections.synchronizedMap(new HashMap<>());

    protected SimpleDateFormat dateFormat;

    public AuthorizationOAuth() {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    protected void setUpKeys() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.genKeyPair();
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        saveKeys(publicKey, privateKey);
        LOG.log(Level.INFO, "Authorization Public Key: {0}", new String(publicKey.getEncoded()));
        LOG.log(Level.INFO, "Authorization Private Key: {0}", new String(privateKey.getEncoded()));
        LOG.log(Level.INFO, "Keys set up in AuthorizationOAuth");
    }

    protected abstract void saveKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) throws Exception;

    private EncryptedJWToken encryptToken(JWToken token) throws Exception {
        EncryptedJWToken encryptedToken = new EncryptedJWToken();
        encryptedToken.setUsername(encryptData(token.getUsername().getBytes()));
        encryptedToken.setUserrole(encryptData(token.getUserrole().getBytes()));
        encryptedToken.setAccessToken(encryptData(token.getAccessToken().getBytes()));
        encryptedToken.setExpirationTime(encryptData(token.getExpirationTime().getBytes()));
        return encryptedToken;
    }

    private JWToken encodeToken(EncryptedJWToken encryptedToken) throws Exception {
        JWToken token = new JWToken();
        token.setUsername(new String(encodeData(encryptedToken.getUsername())));
        token.setUserrole(new String(encodeData(encryptedToken.getUserrole())));
        token.setAccessToken(new String(encodeData(encryptedToken.getAccessToken())));
        token.setExpirationTime(new String(encodeData(encryptedToken.getExpirationTime())));
        return token;
    }

    public JWToken generateJWToken(String username, String userrole) throws Exception {
        LOG.log(Level.INFO, "checking token map");
        if (tokenMap.get(username) == null) {
            LOG.log(Level.INFO, "token not found in map");
            JWToken token = new JWToken();
            token.setUsername(username);
            token.setUserrole(userrole);
            String accessToken = UUID.randomUUID().toString();
            token.setAccessToken(accessToken);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 10);
            token.setExpirationTime(dateFormat.format(cal.getTime()));
            LOG.log(Level.INFO, "token generated, trying to save");
            tokenMap.put(username, token);
            saveJWToken(token.getUsername(), token);
            LOG.log(Level.INFO, "token saved");
            return encodeToken(encryptToken(token));
        }
        return encodeToken(encryptToken(tokenMap.get(username)));
    }

    protected abstract void saveJWToken(String username, JWToken token) throws Exception;

}
