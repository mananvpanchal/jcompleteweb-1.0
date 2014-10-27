/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.oauth2;

/**
 *
 * @author manan
 */
public class OAuthFactory {
    
    protected static OAuthFactory factory;
    
    protected AuthorizationOAuth authorizationOAuth;
    protected ResourceOAuth resourceOAuth;
    
    public static synchronized OAuthFactory getInstance() {
        if(factory==null) {
            factory=new OAuthFactory();
        }
        return factory;
    }
    
    public synchronized AuthorizationOAuth getAuthorizationOAuth(Class<? extends AuthorizationOAuth> clazz) throws Exception {
        if(authorizationOAuth==null) {
            authorizationOAuth=clazz.newInstance();
        }
        return authorizationOAuth;
    }
    
    public synchronized ResourceOAuth getResourceOAuth(Class<? extends ResourceOAuth> clazz) throws Exception {
        if(resourceOAuth==null) {
            resourceOAuth=clazz.newInstance();
        }
        return resourceOAuth;
    }
}
