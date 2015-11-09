/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.TipoProducto;
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
public class TipoProductoServiceImpl implements Serializable, IBaseService<com.artemisa.domain.TipoProducto>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.TipoProducto> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.TipoProducto.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public TipoProducto save(com.artemisa.domain.TipoProducto entity) throws EntityExistsException, HibernateException
    {
        if(this.service.findByField("nombre", entity.getNombre()) == null)
        {
            TipoProducto m = this.service.save(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("El tipo producto con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public TipoProducto update(com.artemisa.domain.TipoProducto entity)  throws EntityExistsException, HibernateException{
        
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "nombre = '" + entity.getNombre() + "'");
        conditions.put("and", "id != " + entity.getId());
        
        List<com.artemisa.domain.TipoProducto> foundTipoProductos = this.service.find(conditions);
        
        if(foundTipoProductos == null)
        {
            TipoProducto m = this.service.update(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("El tipo producto con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public void delete(com.artemisa.domain.TipoProducto entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.TipoProducto> list()
    {
        return this.service.find("nombre");
    }

    @Override
    public TipoProducto findOne(long id) throws HibernateException {
        return this.service.find(id);
    }
}
