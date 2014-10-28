/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.application;

import com.integ.jcompleteweb.oauth.AuthorizationServer;
import com.integ.jcompleteweb.oauth.OAuthFactory;
import com.integ.jcompleteweb.oauth.ResourceOAuth;
import com.integ.jcompleteweb.oauth.ResourceServer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author manan
 */
@ApplicationPath("webresources")
public class WebApplication extends Application {
    
    private static final Logger LOG=Logger.getLogger("JCW_LOGGER");

    public WebApplication() {
        try {
            OAuthFactory.getInstance().getAuthorizationOAuth(AuthorizationServer.class).init();
            OAuthFactory.getInstance().getResourceOAuth(ResourceServer.class).init();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception occurred", ex);
        }
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
