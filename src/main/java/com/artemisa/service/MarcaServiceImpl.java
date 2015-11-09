/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Marca;
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
public class MarcaServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Marca>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Marca> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Marca.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Marca save(com.artemisa.domain.Marca entity) throws EntityExistsException, HibernateException
    {
        if(this.service.findByField("nombre", entity.getNombre()) == null)
        {
            Marca m = this.service.save(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("La marca con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public Marca update(com.artemisa.domain.Marca entity)  throws EntityExistsException, HibernateException{
        
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "nombre = '" + entity.getNombre() + "'");
        conditions.put("and", "id != " + entity.getId());
        
        List<com.artemisa.domain.Marca> foundMarcas = this.service.find(conditions);
        
        if(foundMarcas == null)
        {
            Marca m = this.service.update(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("La marca con el nombre " + entity.getNombre() + " ya existe!");
        }
    }
    
    @Override
    public void delete(com.artemisa.domain.Marca entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Marca> list()
    {
        return this.service.find("nombre");
    }

    @Override
    public Marca findOne(long id) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
