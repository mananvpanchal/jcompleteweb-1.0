/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        ApplicationError error=new ApplicationError();
        error.setMessage(exception.getMessage());
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(outputStream);
        exception.printStackTrace(printStream);
        String stackTrace=outputStream.toString();
        stackTrace=stackTrace.replaceAll("\r\n", "<br/>");
        stackTrace=stackTrace.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        System.out.println(stackTrace);
        error.setStackTrace(stackTrace);
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON).build();
    }
    
}
