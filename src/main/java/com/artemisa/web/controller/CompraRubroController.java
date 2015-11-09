package com.artemisa.web.controller;

import com.artemisa.service.CompraRubroServiceImpl;
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
public class CompraRubroController<CompraRubro> extends BaseController implements Serializable
{
    
    private static final Log logger = LogFactory.getLog(CompraRubroController.class);
    private static final long serialVersionUID = 2978437414279790022L;
    
    @Autowired
    private CompraRubroServiceImpl service;
    
    private com.artemisa.domain.Compra selectedCompra;
    
    private com.artemisa.domain.CompraRubro selected;

    private com.artemisa.domain.CompraRubro newCompraRubro;
    
    private List<com.artemisa.domain.CompraRubro> filter;

    private boolean bonificado; 
    
    public CompraRubroController() 
    {
        newCompraRubro =  new com.artemisa.domain.CompraRubro();
    }

    public com.artemisa.domain.Compra getSelectedCompra() {
        return selectedCompra;
    }

    public void setSelectedCompra(com.artemisa.domain.Compra selectedCompra) {
        this.selectedCompra = selectedCompra;
    }

    
    public com.artemisa.domain.CompraRubro getNewCompraRubro() {
        return newCompraRubro;
    }

    public void setNewCompraRubro(com.artemisa.domain.CompraRubro newCompraRubro) {
        this.newCompraRubro = newCompraRubro;
    }

    public com.artemisa.domain.CompraRubro getSelected() {
        return selected;
    }

    public void setSelected(com.artemisa.domain.CompraRubro selected) {
        this.selected = selected;
    }

    public List<com.artemisa.domain.CompraRubro> getFilter() {
        return filter;
    }

    public void setFilter(List<com.artemisa.domain.CompraRubro> filter) {
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
        if(this.getNewCompraRubro() != null)
        {
            try
            {
                this.getNewCompraRubro().setCompra(compra);
                this.service.save((com.artemisa.domain.CompraRubro)this.getNewCompraRubro());
                
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
                this.service.update((com.artemisa.domain.CompraRubro)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.CompraRubro entity)
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
    
    public List<com.artemisa.domain.CompraRubro> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newCompraRubro = new com.artemisa.domain.CompraRubro();
    }
}
