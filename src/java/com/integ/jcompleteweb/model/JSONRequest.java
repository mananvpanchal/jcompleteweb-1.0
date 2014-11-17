/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.integ.jcompleteweb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manan
 */

@XmlRootElement
public class JSONRequest {

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    protected String criteria;
    
}
