/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.handlers;

import com.integ.jcompleteweb.model.JWToken;
import com.integ.jcompleteweb.oauth.OAuth;
import com.integ.jcompleteweb.oauth.OAuthFactory;
import com.integ.jcompleteweb.oauth.ResourceServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manan
 */
@Path("{path}.html")
public class ResourceHandler {

    static Logger LOG = Logger.getLogger("mylogger");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String handleResource(JWToken token, @PathParam("path") String path) throws Exception {
        LOG.log(Level.INFO, "ResourceHandler POST path: {0}", path);
        if (isPageRequiredAuthentication(path)) {
            if (OAuthFactory.getInstance().getResourceOAuth(ResourceServer.class).validateJWToken(token).equals(OAuth.TOKEN_SUCCEED)) {
                return "success";
            } else {
                return "failure";
            }
        } else {
            return "success";
        }
    }

    public boolean isPageRequiredAuthentication(String page) {
        if (page.equals("login")) {
            return false;
        } else if (page.equals("home")) {
            return true;
        }
        return false;
    }
}
