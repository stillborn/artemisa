package com.artemisa.web.controller;

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
public class ProveedorController<Proveedor> extends BaseController implements Serializable
{
    private static final long serialVersionUID = -1747696151957059371L;
    
    private static final Log logger = LogFactory.getLog(ProveedorController.class);
    
    @Autowired
    private IBaseService<com.artemisa.domain.Proveedor> service;
    
    private Proveedor selected;
    
    private Proveedor newProveedor;
    
    private List<Proveedor> filter;

    public ProveedorController() 
    {
        newProveedor = (Proveedor) new com.artemisa.domain.Proveedor();
    }

    public Proveedor getNewProveedor() {
        return newProveedor;
    }

    public void setNewProveedor(Proveedor newProveedor) {
        this.newProveedor = newProveedor;
    }

    public Proveedor getSelected() {
        return selected;
    }

    public void setSelected(Proveedor selected) {
        this.selected = selected;
    }

    public List<Proveedor> getFilter() {
        return filter;
    }

    public void setFilter(List<Proveedor> filter) {
        this.filter = filter;
    }
    
    public void create()
    {
        if(this.getNewProveedor() != null)
        {
            try
            {
                this.service.save((com.artemisa.domain.Proveedor)this.getNewProveedor());
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
            }
            catch(HibernateException | EntityExistsException | InvalidValueException ex )
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
                this.service.update((com.artemisa.domain.Proveedor)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.Proveedor entity)
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
    
    public List<com.artemisa.domain.Proveedor> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newProveedor = (Proveedor) new com.artemisa.domain.Proveedor();
    }
}
