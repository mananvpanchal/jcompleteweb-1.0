/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.handlers;

import com.integ.jcompleteweb.model.JWToken;
import com.integ.jcompleteweb.oauth.OAuth;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author manan
 */

@Path("home2")
public class HomeResource {
    
    static Logger LOG=Logger.getLogger("mylogger");
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String test(JWToken token) {
        try {
            if(OAuth.validateAccessToken(token)) {                
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception", ex);
            //throw new ApplicationException(ex.getMessage(), ex);
        }
        return "failure";
    }
}
