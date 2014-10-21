/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.filters;

import java.io.IOException;
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
public class CORSFilter implements Filter {
    
    Logger LOG=Logger.getLogger("mylogger");

    private FilterConfig filterConfig = null;
    
    public CORSFilter() {
        
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
        LOG.info("hello before filter");
        LOG.info(((HttpServletRequest)request).getServletPath());
        HttpServletRequest req=(HttpServletRequest)request;
        if(req.getMethod().equals("OPTIONS")) {
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "Content-type");
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "PUT, GET, DELETE, POST, HEAD");
        } else {
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        }
        chain.doFilter(request, response);
        LOG.info("hello after filter");
    }

    /**
     * Return the filter configuration object for this filter.
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
     * Init method for this filter
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
            return ("CORSFilter()");
        }
        StringBuffer sb = new StringBuffer("CORSFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
}
