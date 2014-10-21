/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.handlers;

import com.integ.jcompleteweb.model.JWToken;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
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

@Path("{path}")
public class ResourceHandler {
    
    static Logger LOG=Logger.getLogger("mylogger");
    
    @Context HttpServletRequest request;
    @Context HttpServletResponse response;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String handleResource(JWToken token, @PathParam("path") String path) throws Exception{
        LOG.info("Path:"+path);
        if(!path.contains(".html")) {
            
        } else {
        }
        return "success";
    }
}
