/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.oauth;

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
import java.util.logging.Logger;

/**
 *
 * @author manan
 */
public class AuthorizationServer extends AuthorizationOAuth {

    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    @Override
    protected void saveKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        Statement deleteStmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jcompleteweb", "manan", "12345678");
            con.setAutoCommit(false);
            deleteStmt = con.createStatement();
            deleteStmt.executeUpdate("delete from key_holder");
            stmt = con.prepareStatement("insert into key_holder (id, public_key, private_key) values (?, ?, ?)");
            stmt.setString(1, "keys");
            ByteArrayOutputStream publicKeyOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream publicKeyObjectOutputStream = new ObjectOutputStream(publicKeyOutputStream);
            publicKeyObjectOutputStream.writeObject(publicKey);
            stmt.setBinaryStream(2, new ByteArrayInputStream(publicKeyOutputStream.toByteArray()));
            ByteArrayOutputStream privateKeyOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream privateKeyObjectOutputStream = new ObjectOutputStream(privateKeyOutputStream);
            privateKeyObjectOutputStream.writeObject(privateKey);
            stmt.setBinaryStream(3, new ByteArrayInputStream(privateKeyOutputStream.toByteArray()));
            stmt.execute();
            con.commit();
        } finally {
            if(deleteStmt!=null) {
                deleteStmt.close();
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
    protected void saveJWToken(String name, JWToken token) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement deleteStmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jcompleteweb", "manan", "12345678");
            con.setAutoCommit(false);
            deleteStmt = con.prepareStatement("delete from token_holder where username = ?");
            deleteStmt.setString(1, name);
            deleteStmt.execute();
            stmt = con.prepareStatement("insert into token_holder (username, jwtoken) values (?, ?)");
            stmt.setString(1, name);
            ByteArrayOutputStream tokenOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream tokenObjectOutputStream = new ObjectOutputStream(tokenOutputStream);
            tokenObjectOutputStream.writeObject(token);
            stmt.setBinaryStream(2, new ByteArrayInputStream(tokenOutputStream.toByteArray()));
            stmt.execute();
            con.commit();
        } finally {
            if(deleteStmt!=null) {
                deleteStmt.close();
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
