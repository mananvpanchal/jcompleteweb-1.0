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
public class JSONResponse {

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    protected String data;
    
}
