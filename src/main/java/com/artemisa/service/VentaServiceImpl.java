/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.dao.VentaDao;
import com.artemisa.domain.Venta;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class VentaServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Venta>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private VentaDao service;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Venta.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Venta save(com.artemisa.domain.Venta entity) throws InvalidValueException, EntityExistsException, HibernateException
    {
        if(entity.getFechaVenta() != null)
        {
            if(entity.getCantidad() > 0)
            {
                if(entity.getPrecio() > 0)
                {
                    Venta m = this.service.save(entity);
                    return m;
                }
                else
                {
                    throw new InvalidValueException("El Precio del producto no puede ser cero, por favor asignele precio e intente de nuevo!!");
                }
            }
            else
            {
                throw new InvalidValueException("La cantidad de venta no puede ser cero!!");
            }
        }
        else
        {
            throw new InvalidValueException("La fecha de venta es obligatoria!!");
        }
        
    }
    
    
    
    public long getTotalPriceByDate(Date fecha)
    {
        if(fecha != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            Map<String, String> conditions = new TreeMap<>();
            conditions.put("", "fechaVenta = '"+format.format(fecha) + "'");

            List<Venta> ventas = this.service.find(conditions);
            
            if(ventas != null && ventas.size() > 0)
            {
                long total = 0;
                
                for(Venta v : ventas)
                {
                    total += v.getCantidad() * v.getPrecio();
                }
                return total;
            }                     
        }
        
        return 0;
    } 
    
    @Override
    public Venta update(com.artemisa.domain.Venta entity)  throws EntityExistsException, HibernateException{
        Venta m = this.service.update(entity);
        return m;
    }
    
    @Override
    public void delete(com.artemisa.domain.Venta entity) throws HibernateException {
        if(entity != null)
        {
            this.service.delete(entity);
        }
    }
    
    @Override
    public List<com.artemisa.domain.Venta> list()
    {
        return this.service.find("fecha_venta");
    }

    @Override
    public Venta findOne(long id) throws HibernateException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Object> getVentasByMonth(int month)
    {
        return this.service.getVentasByMonth(month);
    }
}
