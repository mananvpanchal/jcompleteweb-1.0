/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integ.jcompleteweb.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author manan
 */
public class DispatchFilter implements Filter {

    Logger LOG = Logger.getLogger("mylogger");

    private FilterConfig filterConfig = null;

    public DispatchFilter() {

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
        /*LOG.info(((HttpServletRequest) request).getRequestURL().toString());
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getServletPath();
        if ((!path.contains("webresources") && path.endsWith(".jcw")) || !path.endsWith(".html")) {
            path=path.substring(0, path.indexOf(".jcw"))+".html";
            RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(path);
            dispatcher.forward(request, response);
        } else {*/
            chain.doFilter(request, response);
        //}
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
     * Init method for this filter
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
            return ("DispatchFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatchFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

}
