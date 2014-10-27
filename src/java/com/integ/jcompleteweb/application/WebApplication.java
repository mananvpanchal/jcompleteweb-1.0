/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.application;

import com.integ.jcompleteweb.oauth2.AuthorizationServer;
import com.integ.jcompleteweb.oauth2.OAuthFactory;
import com.integ.jcompleteweb.oauth2.ResourceOAuth;
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
        try {
            OAuthFactory.getInstance().getAuthorizationOAuth(AuthorizationServer.class).init();
            OAuthFactory.getInstance().getResourceOAuth(ResourceOAuth.class).init();
        } catch (Exception ex) {

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
