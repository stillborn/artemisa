package com.artemisa.web.controller;

import com.artemisa.domain.CompraDetalle;
import com.artemisa.domain.Producto;
import com.artemisa.domain.ProductoPrecio;
import com.artemisa.service.ProductoPrecioServiceImpl;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
 *
 * @author nicolasrubiano
 */
@Controller
@Scope("session")
public class ProductoPrecioController extends BaseController implements Serializable
{
    private static final Log logger = LogFactory.getLog(ProductoPrecioController.class);
    
    private static final long serialVersionUID = -53692378516216675L;
    
    @Autowired
    private ProductoPrecioServiceImpl service;
    
    private Producto productoSelected;
    
    private CompraDetalle compraDetalle;
    
    private double precio;

    public CompraDetalle getCompraDetalle() {
        return compraDetalle;
    }

    public void setCompraDetalle(CompraDetalle compraDetalle) {
        this.compraDetalle = compraDetalle;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public Producto getProductoSelected() {
        return productoSelected;
    }
    
    public void setProductoSelected(Producto productoSelected) {
        this.productoSelected = productoSelected;
    }
    
    public List<ProductoPrecio> listByProducto()
    {
        if(productoSelected != null)
            return service.listByProducto(productoSelected);
        
        return null;
    }
    
    public void create()
    {        
            try
            {
                ProductoPrecio precioObj = new ProductoPrecio();
                
                precioObj.setFecha(new Date());
                precioObj.setCompraDetalle(compraDetalle);
                precioObj.setPrecio(precio);                                
                
                this.service.save(precioObj);
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
            }
            catch(HibernateException | InvalidValueException ex )
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }        
    }
    
    public void delete(com.artemisa.domain.ProductoPrecio entity)
    {
        if(entity != null)
        {
            try
            {
                this.service.delete(entity);
                
                addMessage("Se ha eliminado correctamente!", FacesMessage.SEVERITY_INFO);
                setDetail(false);
            }
            catch(HibernateException ex)
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }
        }
        else
        {
            addMessage("Debe seleccionar un item válido.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public List<ProductoPrecio> getPrecios(Producto producto)
    {
        if(producto != null)
        {
            return this.service.findPreciosByProducto(producto);
        }
        
        return null;
    }
    
    public void clean()
    {
        this.compraDetalle = null;
        this.precio = 0;       
    }
}
