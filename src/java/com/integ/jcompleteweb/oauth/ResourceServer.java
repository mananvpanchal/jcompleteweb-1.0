/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

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
        Connection con = null;
        Statement stmt = null;
        ResultSet set = null;
        try {
            RSAPublicKey key = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jcompleteweb", "manan", "12345678");
            con.setAutoCommit(false);
            stmt = con.createStatement();
            set = stmt.executeQuery("select public_key from key_holder");
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                key = (RSAPublicKey) objectInputStream.readObject();
            }
            return key;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    @Override
    protected RSAPrivateKey readPrivateKey(String name) throws Exception {
        Connection con = null;
        Statement stmt = null;
        ResultSet set = null;
        try {
            RSAPrivateKey key = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jcompleteweb", "manan", "12345678");
            con.setAutoCommit(false);
            stmt = con.createStatement();
            set = stmt.executeQuery("select private_key from key_holder");
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                key = (RSAPrivateKey) objectInputStream.readObject();
            }
            return key;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    @Override
    protected JWToken readJWToken(String username) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
        try {
            JWToken token = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jcompleteweb", "manan", "12345678");
            con.setAutoCommit(false);
            stmt = con.prepareStatement("select jwtoken from token_holder where username = ?");
            stmt.setString(1, username);
            set = stmt.executeQuery();
            if (set.next()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(set.getBinaryStream(1));
                token = (JWToken) objectInputStream.readObject();
            }
            return token;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
