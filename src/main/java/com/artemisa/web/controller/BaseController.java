package com.artemisa.web.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
/**
 *
 * @author nicolasrubiano
 */
public class BaseController 
{
    private static final Log logger = LogFactory.getLog(BaseController.class);
 
    private boolean detail;

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }
    
    public void addMessage(String message, Severity severity)
    {
        FacesContext.getCurrentInstance().
                addMessage(null,new FacesMessage(severity, message,null));
                
    }
    
    public void addParameter(String key, Object value)
    {
        RequestContext.getCurrentInstance().addCallbackParam(key, value);  
    }
}
