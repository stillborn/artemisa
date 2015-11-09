/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.ProductoPrecioDao;
import com.artemisa.domain.Producto;
import com.artemisa.domain.ProductoPrecio;
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
public class ProductoPrecioServiceImpl implements Serializable, IBaseService<com.artemisa.domain.ProductoPrecio>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private ProductoPrecioDao service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.ProductoPrecio.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.InvalidValueException
     */
    @Override
    public ProductoPrecio save(com.artemisa.domain.ProductoPrecio entity) throws InvalidValueException, HibernateException
    {
        if(entity.getPrecio() != 0)
        {
            ProductoPrecio m = this.service.save(entity);
            return m;
        }
        else
        {
            throw new InvalidValueException("El precio de un producto no puede ser cero");
        }
    }
    
    @Override
    public ProductoPrecio update(com.artemisa.domain.ProductoPrecio entity)  throws EntityExistsException, HibernateException
    {
        ProductoPrecio m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.ProductoPrecio entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    public List<ProductoPrecio> findPreciosByProducto(Producto entity)
    {
         Map<String, String> conditions = new TreeMap<>();
         conditions.put("", "c.producto.id = " + entity.getId());
         
         return this.service.findJoinCompraDetalle(conditions, "fecha desc");
    }
    
    @Override
    public List<com.artemisa.domain.ProductoPrecio> list()
    {
        return this.service.list();
    }
    
    public double calculatePrecio(double base, double percent)
    {
        double price = base + (base * (percent / 100));
        
        return price;
    }
    
    public List<com.artemisa.domain.ProductoPrecio> listByProducto(Producto producto)
    {
        return this.service.listByProducto(producto);
    }

    @Override
    public ProductoPrecio findOne(long id) throws HibernateException {
        return this.service.find(id);
    }
}
