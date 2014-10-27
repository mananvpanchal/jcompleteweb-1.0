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
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author manan
 */
public class AuthorizationServer extends AuthorizationOAuth {
    
    @Override
    protected void savePrivateKey(RSAPrivateKey privateKey) throws IOException {
        saveKey("private.key", privateKey.getModulus(), privateKey.getPrivateExponent());
    }

    @Override
    protected void savePublicKey(RSAPublicKey publicKey) throws IOException {
        saveKey("public.key", publicKey.getModulus(), publicKey.getPublicExponent());
    }
    
    private void saveKey(String name, BigInteger modulus, BigInteger exponent) throws IOException {
        ObjectOutputStream oout = null;
        try {
            oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("F:/java/NetBeansProject/" + name)));
            oout.writeObject(modulus);
            oout.writeObject(exponent);
        } finally {
            if (oout != null) {
                oout.close();
            }
        }
    }
    
    @Override
    protected void saveJWToken(String name, JWToken token) throws IOException {
        ObjectOutputStream oout = null;
        try {
            oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("F:/java/NetBeansProject/" + name)));
            oout.writeObject(token);
        } finally {
            if (oout != null) {
                oout.close();
            }
        }
    }
}
