/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.oauth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author manan
 */
public abstract class OAuth {
    
    private static final Logger LOG=Logger.getLogger("JCW_LOGGER");
    
    public static final String TOKEN_NOT_MATCHED="TOKEN_NOT_MATCHED";
    public static final String TOKEN_TIMED_OUT="TOKEN_TIMED_OUT";
    public static final String TOKEN_SUCCEED="TOKEN_SUCCEED";
    public static final String TOKEN_NOT_FOUND="TOKEN_NOT_FOUND";
    
    protected RSAPublicKey publicKey;
    protected RSAPrivateKey privateKey;
    
    public void init() throws Exception {
        setUpKeys();
    }
    
    protected abstract void setUpKeys() throws Exception;
    
    protected byte[] encryptData(byte[] data) throws Exception {
        //LOG.log(Level.INFO, "Plain: {0}", data);
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherData=cipher.doFinal(data);
       // String cipherStr = new String(cipherData);
        //LOG.log(Level.INFO, "Cipher: {0}", cipherStr);
        return cipherData;
    }
    
    protected byte[] encodeData(byte[] data) {
        return Base64.getEncoder().encode(data);
        //String encoded = new String(Base64.getEncoder().encode(data.getBytes()));
        //LOG.log(Level.INFO, "Encoded: {0}", encoded);
        //return encoded;
    }
    
    protected byte[] decryptData(byte[] data) throws Exception {
        //LOG.log(Level.INFO, "Cipher: {0}", data);
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainData=cipher.doFinal(data);
        //String plainStr = new String(plainData);
        //LOG.log(Level.INFO, "Plain: {0}", plainStr);
        return plainData;
    }   
    
    protected byte[] decodeData(byte[] data) {
        return Base64.getDecoder().decode(data);
        //LOG.log(Level.INFO, "Before Decoded: {0}", data);
        //return new String(Base64.getDecoder().decode(data.getBytes()));
    }
    
}
