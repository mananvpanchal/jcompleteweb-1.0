/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.filters;

import com.google.gson.Gson;
import com.integ.jcompleteweb.model.JWToken;
import com.integ.jcompleteweb.oauth.OAuth;
import com.integ.jcompleteweb.oauth.OAuthFactory;
import com.integ.jcompleteweb.oauth.ResourceServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author manan
 */
public class OAuthFilter implements Filter {

    private FilterConfig filterConfig = null;
    private static final Logger LOG = Logger.getLogger("JCW_LOGGER");

    public OAuthFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        String token = httpServletRequest.getHeader("jwtoken");
        Gson gson = new Gson();
        String requestURI = httpServletRequest.getRequestURI();
        LOG.log(Level.INFO, requestURI);
        String contextPath = httpServletRequest.getContextPath();
        LOG.log(Level.INFO, contextPath);
        LOG.log(Level.INFO, token);
        if (requestURI.contains("restricted")) {
            if (token == null || token.equals("null")) {
                response.getWriter().print("{\"data\":\"AUTHENTICATION_FAILED\"}");
            } else {
                try {
                    JWToken jwToken = gson.fromJson(token, JWToken.class);
                    String status = OAuthFactory.getInstance().getResourceOAuth(ResourceServer.class).validateJWToken(jwToken);
                    LOG.log(Level.INFO, status);
                    if (!status.equals(OAuth.TOKEN_SUCCEED)) {
                        if (httpServletRequest.getMethod().equals("GET")) {
                            response.getWriter().print("Authentication Failed");
                        } else if (httpServletRequest.getMethod().equals("POST")) {
                            response.getWriter().print("{\"data\":\"AUTHENTICATION_FAILED\"}");
                        }
                    } else {
                        chain.doFilter(request, response);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(OAuthFilter.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace(response.getWriter());
                }
            }
        } else {
            chain.doFilter(request, response);
        }
        //chain.doFilter(request, response);
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("OAuthFilter()");
        }
        StringBuilder sb = new StringBuilder("OAuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

}
