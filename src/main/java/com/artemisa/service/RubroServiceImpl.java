/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Rubro;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
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
public class RubroServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Rubro>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Rubro> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Rubro.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Rubro save(com.artemisa.domain.Rubro entity) throws InvalidValueException, EntityExistsException, HibernateException
    {
        Rubro m = this.service.save(entity);
        return m;
    }
    
    
    @Override
    public Rubro update(com.artemisa.domain.Rubro entity)  throws EntityExistsException, HibernateException{
        Rubro m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.Rubro entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Rubro> list()
    {
        return this.service.find("nombre");
    }
    
    @Override
    public Rubro findOne(long id) throws HibernateException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
