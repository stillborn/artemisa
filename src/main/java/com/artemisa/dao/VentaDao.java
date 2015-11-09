/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.dao;

import com.artemisa.domain.Venta;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nicolasrubiano
 */
@org.springframework.transaction.annotation.Transactional(value="hibernateTransactionManager")
@Repository
public class VentaDao extends BaseDao<Venta> 
{
    private static final long serialVersionUID = 3491491999165474647L; 
    
    public List<Object> getVentasByMonth(int month)
    {
        String query = "select fecha_venta, sum(precio) as total from venta where "
                        + "month(fecha_venta) = "+month+" group by fecha_venta";
        
        List<Object> list = this.sessionFactory
                                    .getCurrentSession()
                                    .createSQLQuery(query).list();
        
        return list;
    }
}
