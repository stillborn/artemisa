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
public class PresentacionController<Presentacion> extends BaseController implements Serializable
{
    private static final long serialVersionUID = -1747696151957059371L;
    
    private static final Log logger = LogFactory.getLog(PresentacionController.class);
    
    @Autowired
    private IBaseService<com.artemisa.domain.Presentacion> service;
    
    private Presentacion selected;
    
    private Presentacion newPresentacion;
    
    private List<Presentacion> filter;

    public PresentacionController() 
    {
        newPresentacion = (Presentacion) new com.artemisa.domain.Presentacion();
    }

    public Presentacion getNewPresentacion() {
        return newPresentacion;
    }

    public void setNewPresentacion(Presentacion newPresentacion) {
        this.newPresentacion = newPresentacion;
    }

    public Presentacion getSelected() {
        return selected;
    }

    public void setSelected(Presentacion selected) {
        this.selected = selected;
    }

    public List<Presentacion> getFilter() {
        return filter;
    }

    public void setFilter(List<Presentacion> filter) {
        this.filter = filter;
    }
    
    public void create()
    {
        if(this.getNewPresentacion() != null)
        {
            try
            {
                this.service.save((com.artemisa.domain.Presentacion)this.getNewPresentacion());
                
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
                this.service.update((com.artemisa.domain.Presentacion)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.Presentacion entity)
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
    
    public List<com.artemisa.domain.Presentacion> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newPresentacion = (Presentacion) new com.artemisa.domain.Presentacion();
    }
}
