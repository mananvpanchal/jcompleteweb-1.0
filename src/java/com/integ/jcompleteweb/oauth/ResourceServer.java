/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

import com.integ.jcompleteweb.database.Database;
import com.integ.jcompleteweb.database.DatabaseFactory;
import com.integ.jcompleteweb.model.JWToken;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author manan
 */
public class ResourceServer extends ResourceOAuth {

    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    @Override
    protected RSAPublicKey readPublicKey(String name) throws Exception {
        Database database=DatabaseFactory.getInstance().createDatabase();
        try {
            RSAPublicKey key = null;
            database.open();
            database.start();
            ResultSet set=database.executeQuery("select public_key from key_holder");
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                key = (RSAPublicKey) objectInputStream.readObject();
            }
            database.end();
            database.commit();
            return key;
        } finally {
            database.close();
        }
    }

    @Override
    protected RSAPrivateKey readPrivateKey(String name) throws Exception {
        Database database=DatabaseFactory.getInstance().createDatabase();
        try {
            RSAPrivateKey key = null;
            database.open();
            database.start();
            ResultSet set=database.executeQuery("select private_key from key_holder");
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                key = (RSAPrivateKey) objectInputStream.readObject();
            }
            database.end();
            database.commit();
            return key;
        } finally {
            database.close();
        }
    }

    @Override
    protected JWToken readJWToken(String username) throws Exception {
        Database database=DatabaseFactory.getInstance().createDatabase();
        try {
            JWToken token = null;
            database.open();
            database.start();
            ResultSet set=database.executePreparedQuery("select jwtoken from token_holder where username = ?", new Object[]{username});
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                token = (JWToken) objectInputStream.readObject();
            }
            database.end();
            database.commit();
            return token;
        } finally {
            database.close();
        }
    }
}
