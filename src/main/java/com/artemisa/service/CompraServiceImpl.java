/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Compra;
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
public class CompraServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Compra>
{
    private static final long serialVersionUID = -2168114926545089692L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Compra> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Compra.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Compra save(com.artemisa.domain.Compra entity) throws EntityExistsException, HibernateException
    {
        Compra m = this.service.save(entity);
        return m;
    }
    
    @Override
    public Compra update(com.artemisa.domain.Compra entity)  throws EntityExistsException, HibernateException
    {    
        Compra m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.Compra entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Compra> list()
    {
        return this.service.find("fecha_compra");
    }

    @Override
    public Compra findOne(long id) throws HibernateException 
    {
        return this.service.find(id);
    }
}
