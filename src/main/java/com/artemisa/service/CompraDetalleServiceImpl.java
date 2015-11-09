/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.CompraDetalle;
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
public class CompraDetalleServiceImpl implements Serializable, IBaseService<com.artemisa.domain.CompraDetalle>
{
    private static final long serialVersionUID = 8157203112407890202L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.CompraDetalle> service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.CompraDetalle.class);
    }
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     * @throws com.artemisa.service.exceptions.InvalidValueException
     */
    @Override
    public CompraDetalle save(com.artemisa.domain.CompraDetalle entity) throws EntityExistsException, HibernateException, InvalidValueException
    {
        if(entity.getCantidad() > 0)
        { 
            if(entity.getBonificado() || entity.getPrecio() > 0 ){

                CompraDetalle m = this.service.save(entity);
                return m;
            }
            else
            {
                throw new InvalidValueException("El precio no puede ser cero.");
            }
        }
        else
        {
            throw new InvalidValueException("La cantidad facturada no puede ser cero.");
        }
    }
    
    /**
     *
     * @param entity
     * @param precioTotal
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     * @throws com.artemisa.service.exceptions.InvalidValueException
     */    
    public CompraDetalle save(com.artemisa.domain.CompraDetalle entity, double precioTotal) throws EntityExistsException, HibernateException, InvalidValueException
    {
        if(entity.getCantidad() > 0)
        { 
            if(entity.getBonificado() || (precioTotal > 0 || entity.getPrecio() > 0)){
                
                if(precioTotal > 0)
                {
                    double precioUnitario = precioTotal / entity.getCantidad();
                    entity.setPrecio(precioUnitario);
                }
                
                CompraDetalle m = this.service.save(entity);
                return m;
            }
            else
            {
                throw new InvalidValueException("El precio no puede ser cero.");
            }
        }
        else
        {
            throw new InvalidValueException("La cantidad facturada no puede ser cero.");
        }
    }
    
    @Override
    public CompraDetalle update(com.artemisa.domain.CompraDetalle entity)  throws EntityExistsException, HibernateException
    {
        CompraDetalle m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.CompraDetalle entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    
    @Override
    public List<com.artemisa.domain.CompraDetalle> list()
    {
        return this.service.list();
    }
    
    public List<com.artemisa.domain.CompraDetalle> getCompraDetalleByCompra(long idCompra)
    {
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "compra = " + idCompra);
        
        return (List<CompraDetalle>) this.service.find(conditions, "id desc");
    }
    
    @Override
    public CompraDetalle findOne(long id) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
