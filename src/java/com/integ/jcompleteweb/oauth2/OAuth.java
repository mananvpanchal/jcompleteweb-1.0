/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.oauth2;

import com.sun.xml.wss.impl.misc.Base64;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author manan
 */
public abstract class OAuth {
    
    public static final String TOKEN_NOT_MATCHED="TOKEN_NOT_MATCHED";
    public static final String TOKEN_TIMED_OUT="TOKEN_TIMED_OUT";
    public static final String TOKEN_SUCCEED="TOKEN_SUCCEED";
    
    protected RSAPublicKey publicKey;
    protected RSAPrivateKey privateKey;
    
    public void init() throws Exception {
        setUpKeys();
    }
    
    protected abstract void setUpKeys() throws Exception;
    
    protected String encryptData(String data) throws Exception {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherData=cipher.doFinal(data.getBytes());
        String cipherStr= Base64.encode(cipherData);
        return cipherStr;
    }
    
    protected String decryptData(String data) throws Exception {
        byte[] cipherData=Base64.decode(data.getBytes());
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainData=cipher.doFinal(cipherData);
        return new String(plainData);
    }   
    
}
