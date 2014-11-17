/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.exception;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manan
 */

@XmlRootElement
public class ApplicationError {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    protected String message;
    protected String stackTrace;
    
}
