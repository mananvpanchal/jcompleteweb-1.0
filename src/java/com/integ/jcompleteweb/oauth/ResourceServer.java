/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.model.JWToken;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 *
 * @author manan
 */
public class ResourceServer extends ResourceOAuth {
    
    protected RSAPublicKey readPublicKey(String name) throws Exception {
        BigInteger[] key = readKey(name);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(key[0], key[1]);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) fact.generatePublic(keySpec);
    }
    
    protected RSAPrivateKey readPrivateKey(String name) throws Exception {
        BigInteger[] key = readKey(name);
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(key[0], key[1]);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) fact.generatePrivate(keySpec);
    }

    private BigInteger[] readKey(String name) throws Exception {
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(new FileInputStream("F:/java/NetBeansProject/" + name)));
        BigInteger[] key = new BigInteger[2];
        try {
            key[0] = (BigInteger) oin.readObject();
            key[1] = (BigInteger) oin.readObject();
        } finally {
            oin.close();
        }
        return key;
    }

    @Override
    protected JWToken readJWToken(String username) throws Exception {
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(new FileInputStream("F:/java/NetBeansProject/" + username)));
        JWToken token;
        try {
            token = (JWToken) oin.readObject();
        } finally {
            oin.close();
        }
        return token;
    }
}
