/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Presentacion;
import com.artemisa.service.exceptions.EntityExistsException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasrubiano
 */
@Service()
public class PresentacionServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Presentacion>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Presentacion> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Presentacion.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Presentacion save(com.artemisa.domain.Presentacion entity) throws EntityExistsException, HibernateException
    {
        if(this.service.findByField("descripcion", entity.getDescripcion()) == null)
        {
            Presentacion m = this.service.save(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("La presentación con la descripción " + entity.getDescripcion() + " ya existe!");
        }
    }
    
    @Override
    public Presentacion update(com.artemisa.domain.Presentacion entity)  throws EntityExistsException, HibernateException{
        
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "descripcion= '" + entity.getDescripcion()+ "'");
        conditions.put("and", "id != " + entity.getId());
        
        List<com.artemisa.domain.Presentacion> foundPresentacions = this.service.find(conditions);
        
        if(foundPresentacions == null)
        {
            Presentacion m = this.service.update(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("La presentación con la descripción " + entity.getDescripcion() + " ya existe!");
        }
    }
    
    @Override
    public void delete(com.artemisa.domain.Presentacion entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Presentacion> list()
    {
        return this.service.find("descripcion");
    }

    @Override
    public Presentacion findOne(long id) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
