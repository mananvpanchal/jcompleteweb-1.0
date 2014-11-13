/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.database.Database;
import com.integ.jcompleteweb.database.DatabaseFactory;
import com.integ.jcompleteweb.model.JWToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author manan
 */
public class AuthorizationServer extends AuthorizationOAuth {

    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    @Override
    protected void saveKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) throws Exception {
        Database database=DatabaseFactory.getInstance().createDatabase();
        try {
            database.open();
            database.start();
            database.executeUpdate("delete from key_holder");
            
            ByteArrayOutputStream publicKeyOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream publicKeyObjectOutputStream = new ObjectOutputStream(publicKeyOutputStream);
            publicKeyObjectOutputStream.writeObject(publicKey);
            
            ByteArrayOutputStream privateKeyOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream privateKeyObjectOutputStream = new ObjectOutputStream(privateKeyOutputStream);
            privateKeyObjectOutputStream.writeObject(privateKey);
            
            database.executePreparedUpdate("insert into key_holder (id, public_key, private_key, modifieddate) values (?, ?, ?, ?)", 
                    new Object[]{"keys", 
                        new ByteArrayInputStream(publicKeyOutputStream.toByteArray()), 
                        new ByteArrayInputStream(privateKeyOutputStream.toByteArray()), 
                        new Date()
                    });
            database.end();
            database.commit();
        } finally {
            database.close();
        }
    }

    @Override
    protected void saveJWToken(String name, JWToken token) throws Exception {
        Database database=DatabaseFactory.getInstance().createDatabase();
        try {
            database.open();
            database.start();
            database.executePreparedUpdate("delete from token_holder where username = ?", new Object[]{name});
            
            ByteArrayOutputStream tokenOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream tokenObjectOutputStream = new ObjectOutputStream(tokenOutputStream);
            tokenObjectOutputStream.writeObject(token);
            database.executePreparedUpdate("insert into token_holder (username, jwtoken, modifieddate) values (?, ?, ?)", 
                    new Object[]{name, 
                        new ByteArrayInputStream(tokenOutputStream.toByteArray()), new Date()});
            database.end();
            database.commit();
        } finally {
            database.close();
        }
    }

}
