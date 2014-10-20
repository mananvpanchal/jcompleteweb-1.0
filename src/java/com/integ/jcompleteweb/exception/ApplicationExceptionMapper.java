/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author manan
 */

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException exception) {
        ApplicationError error=new ApplicationError();
        error.setErrorMessage(exception.getMessage());
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON).build();
    }
    
}
