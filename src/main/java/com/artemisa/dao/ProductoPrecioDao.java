/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.dao;

import com.artemisa.domain.Producto;
import com.artemisa.domain.ProductoPrecio;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicolasrubiano
 */
@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
@Repository
public class ProductoPrecioDao extends BaseDao<ProductoPrecio> 
{
    private static final long serialVersionUID = -5044059926060067406L;
    
    public List<ProductoPrecio> findJoinCompraDetalle(Map<String, String> conditions, String order)
    {
        String query = "select pp from ProductoPrecio pp inner join pp.compraDetalle as c";
        
        if (!conditions.isEmpty()) 
        {
            String whereClausule = "";
        
            for(Map.Entry<String, String> condition : conditions.entrySet())
            {
                if(whereClausule.isEmpty())
                {
                    whereClausule = condition.getValue();
                }
                else
                {
                    whereClausule = whereClausule + " " + condition.getKey() + " " + condition.getValue();
                }
            }
            
            query += " where " + whereClausule;
        }
        
        if(!order.isEmpty())
        {
            query = query + " order by " + order; 
        }
        
        List<ProductoPrecio> list = this.sessionFactory.getCurrentSession().createQuery(query)
                .list();
        
        if(list.isEmpty())
        {
            return null;
        }
        
        return list;
    }
    
    public List<ProductoPrecio> listByProducto(Producto producto)
    {
        String query = "from " + this.getType().getName() + " where producto = :producto";
        
        return this.sessionFactory.getCurrentSession().createQuery(query)
                .setLong("producto", producto.getId())
                .list();
    }
}
