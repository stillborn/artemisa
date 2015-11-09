package com.artemisa.web.controller;

import com.artemisa.service.IBaseService;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class RubroController<Rubro> extends BaseController implements Serializable
{
    private static final Log logger = LogFactory.getLog(RubroController.class);
    private static final long serialVersionUID = -7565109381860268465L;
    
    @Autowired
    private IBaseService<com.artemisa.domain.Rubro> service;
    
    private Rubro selected;
    
    private Rubro newRubro;
    
    private List<Rubro> filter;

    public RubroController() 
    {
        newRubro = (Rubro) new com.artemisa.domain.Rubro();
    }

    public Rubro getNewRubro() {
        return newRubro;
    }

    public void setNewRubro(Rubro newRubro) {
        this.newRubro = newRubro;
    }

    public Rubro getSelected() {
        return selected;
    }

    public void setSelected(Rubro selected) {
        this.selected = selected;
    }

    public List<Rubro> getFilter() {
        return filter;
    }

    public void setFilter(List<Rubro> filter) {
        this.filter = filter;
    }
    
    public void create() throws InvalidValueException
    {
        if(this.getNewRubro() != null)
        {
            try
            {
                this.service.save((com.artemisa.domain.Rubro)this.getNewRubro());
                
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
                this.service.update((com.artemisa.domain.Rubro)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.Rubro entity)
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
    
    public List<com.artemisa.domain.Rubro> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newRubro = (Rubro) new com.artemisa.domain.Rubro();
    }
}
