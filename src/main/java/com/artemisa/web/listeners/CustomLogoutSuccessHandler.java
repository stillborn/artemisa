package com.artemisa.web.listeners;

import com.artemisa.domain.User;
import com.artemisa.service.UserServiceImpl;
import com.artemisa.service.exceptions.EntityExistsException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;


@Component("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
    
    @Autowired
    @Qualifier("userServiceImpl")
    private UserServiceImpl userService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {        
        
        try
        {            
            User userAuth = (User)authentication.getPrincipal();
            userAuth.setLogged(false);
            
            userService.update(userAuth);            
        }
        catch(EntityExistsException | HibernateException ex)
        {
            ex.printStackTrace();
        }
        
        setDefaultTargetUrl("/login.xhtml");
        super.onLogoutSuccess(request, response, authentication);
    }
}
