/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.handlers;

import com.integ.jcompleteweb.model.JSONRequest;
import com.integ.jcompleteweb.model.JSONResponse;
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

@Path("restricted/home")
public class HomeResource {
    
    static Logger LOG=Logger.getLogger("mylogger");
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONResponse test(JSONRequest request) {
        JSONResponse response=new JSONResponse();
        response.setData("Hello "+request.getCriteria());
        return response;
    }
}
