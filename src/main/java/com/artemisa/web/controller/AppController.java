package com.artemisa.web.controller;

import com.artemisa.domain.User;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


@Controller
@Scope("request")
public class AppController implements Serializable
{
    private static final long serialVersionUID = -1747696151957059371L;
    
    private static final Log logger = LogFactory.getLog(AppController.class);
    
    private String username;
    
    private String password;
    
    
    public String getAppUploadPath()
    {
        return "/uploads";
    }
    
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public User getAuthenticatedUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public String login() throws ServletException, IOException {
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                        .getRequestDispatcher("/login");

        dispatcher.forward((ServletRequest) context.getRequest(),
                        (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        
        return null;
    }
}
