/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.handlers;

import com.google.gson.Gson;
import com.integ.jcompleteweb.model.JWToken;
import com.integ.jcompleteweb.oauth.OAuth;
import com.integ.jcompleteweb.oauth.OAuthFactory;
import com.integ.jcompleteweb.oauth.ResourceServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manan
 */
@Path("/restricted/{path}.html")
public class ResourceHandler {

    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    @Context
    HttpServletRequest request;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String handleResource(@PathParam("path") String path) throws Exception {
        LOG.log(Level.INFO, "ResourceHandler POST path: {0}", path);
        String token = request.getHeader("jwtoken");
        Gson gson = new Gson();
        if (token != null && !token.equals("null")) {
            JWToken jwToken = gson.fromJson(token, JWToken.class);
            if (OAuthFactory.getInstance().getResourceOAuth(ResourceServer.class).validateJWToken(jwToken).equals(OAuth.TOKEN_SUCCEED)) {
                return "success";
            } else {
                return "failure";
            }
        } else {
            return "failure";
        }
    }

}
