package com.artemisa.web.controller;

import com.artemisa.domain.Producto;
import com.artemisa.service.CompraDetalleServiceImpl;
import com.artemisa.service.IBaseService;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class CompraDetalleController<CompraDetalle> extends BaseController implements Serializable
{
    
    private static final Log logger = LogFactory.getLog(CompraDetalleController.class);
    
    private static final long serialVersionUID = -7658912139992376450L;
    
    @Autowired
    private CompraDetalleServiceImpl service;
    
    private com.artemisa.domain.Compra selectedCompra;
    
    private com.artemisa.domain.CompraDetalle selected;

    private com.artemisa.domain.CompraDetalle newCompraDetalle;
    
    private List<com.artemisa.domain.CompraDetalle> filter;

    private boolean bonificado; 
    
    private double precioTotal;

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
    
    public CompraDetalleController() 
    {
        newCompraDetalle =  new com.artemisa.domain.CompraDetalle();
    }

    public com.artemisa.domain.Compra getSelectedCompra() {
        return selectedCompra;
    }

    public void setSelectedCompra(com.artemisa.domain.Compra selectedCompra) {
        this.selectedCompra = selectedCompra;
    }

    
    public com.artemisa.domain.CompraDetalle getNewCompraDetalle() {
        return newCompraDetalle;
    }

    public void setNewCompraDetalle(com.artemisa.domain.CompraDetalle newCompraDetalle) {
        this.newCompraDetalle = newCompraDetalle;
    }

    public com.artemisa.domain.CompraDetalle getSelected() {
        return selected;
    }

    public void setSelected(com.artemisa.domain.CompraDetalle selected) {
        this.selected = selected;
    }

    public List<com.artemisa.domain.CompraDetalle> getFilter() {
        return filter;
    }

    public void setFilter(List<com.artemisa.domain.CompraDetalle> filter) {
        this.filter = filter;
    }

    public boolean isBonificado() {
        return bonificado;
    }

    public void setBonificado(boolean bonificado) {
        this.bonificado = bonificado;
    }

    public void create(com.artemisa.domain.Compra compra)
    {
        if(this.getNewCompraDetalle() != null)
        {
            try
            {    
                this.getNewCompraDetalle().setCompra(compra);
                this.service.save((com.artemisa.domain.CompraDetalle)this.getNewCompraDetalle(), precioTotal);
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
            }
            catch(HibernateException | InvalidValueException | EntityExistsException ex )
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            } 
        }
        else
        {
            addMessage("Debe seleccionar un item valido.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void update()
    {
        if(this.getSelected() != null)
        {
            try
            {
                this.service.update((com.artemisa.domain.CompraDetalle)this.getSelected());
                
                addMessage("Se ha actualizado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
            }
            catch(HibernateException | EntityExistsException ex)
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
    
    public void delete(com.artemisa.domain.CompraDetalle entity)
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
    
    public List<com.artemisa.domain.CompraDetalle> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newCompraDetalle = new com.artemisa.domain.CompraDetalle();
        this.precioTotal = 0;
    }
}
