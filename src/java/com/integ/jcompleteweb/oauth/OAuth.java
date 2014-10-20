/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.model.JWToken;
import com.sun.xml.wss.impl.misc.Base64;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author manan
 */
public class OAuth {
    
    static Logger LOG=Logger.getLogger("mylogger");

    protected static HashMap<String, String> userTokenMap = new HashMap<>();
    protected static KeyPair keyPair;

    public static void init() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.genKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, "Exception", ex);
        }
    }
    
    public static String encryptData(String data) throws Exception {
        LOG.info("Plain data: "+data);
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cipherData=cipher.doFinal(data.getBytes());
        LOG.info("Cipher data: "+new String(cipherData));
        String cipherStr= Base64.encode(cipherData);
        LOG.info("Enoded data: "+cipherStr);
        return cipherStr;
    }
    
    public static String decryptData(String data) throws Exception {
        LOG.info("Enoded data: "+data);
        byte[] cipherData=Base64.decode(data.getBytes());
        LOG.info("Cipher data: "+new String(cipherData));
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] plainData=cipher.doFinal(cipherData);
        LOG.info("Plain data: "+new String(plainData));
        return new String(plainData);
    }
    
    public static JWToken generateJWToken(String username, String userRole) throws Exception {
        JWToken token=new JWToken();
        token.setUsername(encryptData(username));
        token.setUserrole(encryptData(userRole));
        String accessToken = UUID.randomUUID().toString();
        userTokenMap.put(username, accessToken);
        token.setAccessToken(encryptData(accessToken));
        return token;
    }
    
    public static boolean validateAccessToken(JWToken token) throws Exception {
        return validateAccessToken(decryptData(token.getUsername()), decryptData(token.getAccessToken()));
    }

    public static boolean validateAccessToken(String username, String token) {
        return userTokenMap.get(username).equals(token);
    }

}
