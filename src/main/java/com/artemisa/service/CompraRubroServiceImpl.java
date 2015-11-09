/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.CompraRubro;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
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
public class CompraRubroServiceImpl implements Serializable, IBaseService<com.artemisa.domain.CompraRubro>
{
    private static final long serialVersionUID = 8157203112407890202L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.CompraRubro> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.CompraRubro.class);
    }
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     * @throws com.artemisa.service.exceptions.InvalidValueException
     */
    @Override
    public CompraRubro save(com.artemisa.domain.CompraRubro entity) throws EntityExistsException, HibernateException, InvalidValueException
    {
        CompraRubro m = this.service.save(entity);
        return m;
    }
    
    @Override
    public CompraRubro update(com.artemisa.domain.CompraRubro entity)  throws EntityExistsException, HibernateException
    {
        CompraRubro m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.CompraRubro entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    
    @Override
    public List<com.artemisa.domain.CompraRubro> list()
    {
        return this.service.list();
    }
    
    public List<com.artemisa.domain.CompraRubro> getCompraRubroByCompra(long idCompra)
    {
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "id_compra = " + idCompra);
        
        return (List<CompraRubro>) this.service.find(conditions);
    }
    
    @Override
    public CompraRubro findOne(long id) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
