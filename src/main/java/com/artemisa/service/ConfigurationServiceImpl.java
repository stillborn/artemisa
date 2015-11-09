/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Configuration;
import com.artemisa.service.exceptions.EntityExistsException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasrubiano
 */
@Service()
public class ConfigurationServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Configuration>
{
    public static final int DAYS_TO_CADUCATE_PASSWORD = 1;
    
    private static final long serialVersionUID = -2168114926545089692L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Configuration> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Configuration.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Configuration save(com.artemisa.domain.Configuration entity) throws EntityExistsException, HibernateException
    {
        Configuration m = this.service.save(entity);
        return m;
    }
    
    @Override
    public Configuration update(com.artemisa.domain.Configuration entity)  throws EntityExistsException, HibernateException
    {    
        Configuration m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.Configuration entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Configuration> list()
    {
        return this.service.list();
    }

    @Override
    public Configuration findOne(long id) throws HibernateException 
    {
        return this.service.find(id);
    }
}
