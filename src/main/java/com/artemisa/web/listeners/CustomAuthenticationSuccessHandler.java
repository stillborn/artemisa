package com.artemisa.web.listeners;

import com.artemisa.domain.User;
import com.artemisa.service.ConfigurationServiceImpl;
import com.artemisa.service.UserServiceImpl;
import com.artemisa.service.exceptions.ConfigurationNotFoundException;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.ExpiredCredentialsException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler{
    
    @Autowired
    private ConfigurationServiceImpl configurationService;
    
    @Autowired
    @Qualifier("userServiceImpl")
    private UserServiceImpl userService;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        try {
            super.onAuthenticationSuccess(request, response, authentication);
            
            User user = (User)this.userService.findOneByUserName(authentication.getName());
            
            Date lastLogin = user.getLastLoginDate();
            user.setFailedAuthenticationAttemps(0);
            user.setLastLoginDate(new Date());
            user.setLogged(true);
            
            try {
                if(this.verifyUserExpiredCredentials(authentication.getName())){
                    
                    authentication.setAuthenticated(false);
                    ExpiredCredentialsException ex = new ExpiredCredentialsException("Sus Credenciales Han Caducado");
                    
                    user.setLastLoginDate(lastLogin);
                    user.setLogged(false);
                    
                    throw ex;
                }
            } catch (NumberFormatException | ConfigurationNotFoundException e) {
                e.printStackTrace();
            } catch (ExpiredCredentialsException e) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e);
                e.printStackTrace();
            }
            
            userService.update(user);
        } catch (EntityExistsException | HibernateException ex) {
            Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean verifyUserExpiredCredentials(String username) throws NumberFormatException, ConfigurationNotFoundException{
        
        User userValidation = userService.findOneByUserName(username);
        
        if(userValidation != null){
            
            User user = (User) userService.findOneByUserName(username);
            
            Date lastLogin = user.getLastLoginDate();
            DateTime lastLoginTime = new DateTime(lastLogin);
            
            int days = ConfigurationServiceImpl.DAYS_TO_CADUCATE_PASSWORD;
            String value = configurationService.findOne(days).getConfigurationValue();
            int daysToExpire = Integer.parseInt(value);
            
            int daysDifference = Days.daysBetween(lastLoginTime, new DateTime()).getDays();
            
            if(daysDifference >= daysToExpire){
                user.setCredentialsNonExpired(false);
                
                try {
                    this.userService.update(user);
                } catch (EntityExistsException | HibernateException ex) {
                    Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        }
        
        return false;
    }
}
