/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.application;

import com.integ.jcompleteweb.oauth.OAuth;
import com.integ.jcompleteweb.handlers.AuthenticationHandler;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author manan
 */
@ApplicationPath("webresources")
public class WebApplication extends Application {
    
    public WebApplication() {
        OAuth.init();
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.integ.jcompleteweb.exception.ApplicationExceptionMapper.class);
        resources.add(com.integ.jcompleteweb.handlers.AuthenticationHandler.class);
        resources.add(com.integ.jcompleteweb.handlers.HomeResource.class);
        resources.add(com.integ.jcompleteweb.handlers.ResourceHandler.class);
    }
}
