/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.UserDao;
import com.artemisa.domain.User;
import com.artemisa.service.exceptions.EntityExistsException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasrubiano
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService, Serializable
{
    private static final long serialVersionUID = -2168114926545089692L;
    
    @Autowired
    private UserDao service;
        
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */    
    public User save(com.artemisa.domain.User entity) throws EntityExistsException, HibernateException
    {
        User m = this.service.save(entity);
        return m;
    }
        
    public User update(com.artemisa.domain.User entity)  throws EntityExistsException, HibernateException
    {    
        User m = this.service.update(entity);
        return m;
    }
        
    public void delete(com.artemisa.domain.User entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
        
    public List<com.artemisa.domain.User> list()
    {
        return this.service.list();
    }
    
    public User findOne(long id) throws HibernateException 
    {
        return this.service.find(id);
    }
    
    public User findOneByUserName(String userName) throws HibernateException 
    {
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "username = '"+userName+"'");
        
        List<User> users = this.service.find(conditions);
        
        
        if(users != null)
        {
            return users.get(0);
        }
        
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findOneByUserName(username);
    }
}
