/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.handlers;

import com.integ.jcompleteweb.exception.ApplicationException;
import com.integ.jcompleteweb.model.Credentials;
import com.integ.jcompleteweb.model.JWToken;
import com.integ.jcompleteweb.oauth.AuthorizationServer;
import com.integ.jcompleteweb.oauth.OAuthFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manan
 */

@Path("open/dologin")
public class AuthenticationHandler {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JWToken doLogin(Credentials credentials) throws ApplicationException {
        try {
            
            return OAuthFactory.getInstance().getAuthorizationOAuth(AuthorizationServer.class).generateJWToken(credentials.getUsername(), "ADMIN");
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
