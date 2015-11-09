/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Proveedor;
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
public class ProveedorServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Proveedor>
{
    private static final long serialVersionUID = 1143272565125899675L;
   
    @Autowired
    private BaseDao<com.artemisa.domain.Proveedor> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Proveedor.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Proveedor save(com.artemisa.domain.Proveedor entity) throws EntityExistsException, HibernateException
    {
        if(this.service.findByField("nombre", entity.getNombre()) == null)
        {
            Proveedor m = this.service.save(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("El proveedor con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public Proveedor update(com.artemisa.domain.Proveedor entity)  throws EntityExistsException, HibernateException{
        
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "nombre = '" + entity.getNombre() + "'");
        conditions.put("and", "id != " + entity.getId());
        
        List<com.artemisa.domain.Proveedor> foundProveedors = this.service.find(conditions);
        
        if(foundProveedors == null)
        {
            Proveedor m = this.service.update(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("El proveedor con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public void delete(com.artemisa.domain.Proveedor entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Proveedor> list()
    {
        return this.service.find("nombre");
    }

    @Override
    public Proveedor findOne(long id) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
