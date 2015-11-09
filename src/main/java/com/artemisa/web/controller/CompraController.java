package com.artemisa.web.controller;

import com.artemisa.domain.CompraDetalle;
import com.artemisa.domain.Rubro;
import com.artemisa.service.CompraDetalleServiceImpl;
import com.artemisa.service.CompraRubroServiceImpl;
import com.artemisa.service.CompraServiceImpl;
import com.artemisa.service.IBaseService;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class CompraController<Compra> extends BaseController implements Serializable
{
    
    private static final Log logger = LogFactory.getLog(CompraController.class);
    
    private static final long serialVersionUID = 8807889700267767520L;
    
    
    @Autowired
    private CompraServiceImpl service;
    
    @Autowired
    private CompraDetalleServiceImpl compraDetalleServiceImpl;
    
    @Autowired
    private CompraRubroServiceImpl compraRubroServiceImpl;
    
    private com.artemisa.domain.Compra selected;
    
    
    private com.artemisa.domain.Compra newCompra;
    
    private com.artemisa.domain.CompraDetalle newCompraDetalle;
    
    private List<Compra> filter;
    
    public CompraController()
    {
        newCompra = new com.artemisa.domain.Compra();
        newCompraDetalle = new com.artemisa.domain.CompraDetalle();
    }
    
    public CompraDetalle getNewCompraDetalle() {
        return newCompraDetalle;
    }
    
    public void setNewCompraDetalle(CompraDetalle newCompraDetalle) {
        this.newCompraDetalle = newCompraDetalle;
    }
    
    public com.artemisa.domain.Compra getNewCompra() {
        return newCompra;
    }
    
    public void setNewCompra(com.artemisa.domain.Compra newCompra) {
        this.newCompra = newCompra;
    }
    
    public com.artemisa.domain.Compra getSelected() {
        return selected;
    }
    
    public void setSelected(com.artemisa.domain.Compra selected) {
        this.selected = selected;
    }
    
    public List<Compra> getFilter() {
        return filter;
    }
    
    public void setFilter(List<Compra> filter) {
        this.filter = filter;
    }
    
    public void setSelectedCompra()
    {
        Map<String,String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        
        String compraIdString = params.get("compraId");
        
        if(compraIdString != null)
        {
            int compraId = Integer.parseInt(compraIdString);
            
            this.selected = this.service.findOne((long)compraId);
        }
    }
    
    public void create() throws InvalidValueException
    {
        if(this.getNewCompra() != null)
        {
            try
            {
                com.artemisa.domain.Compra c = this.service.save((com.artemisa.domain.Compra)this.getNewCompra());
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("editar.xhtml?idCompra=" + c.getId().toString());
            }
            catch(HibernateException | EntityExistsException ex )
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }
            catch (IOException ex)
            {
                Logger.getLogger(CompraController.class.getName()).log(Level.SEVERE, null, ex);
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
                this.service.update((com.artemisa.domain.Compra)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.Compra entity)
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
    
    public List<com.artemisa.domain.CompraDetalle> getCompraDetalleOfSelected()
    {
        if(selected != null)
        {
            return this.compraDetalleServiceImpl.getCompraDetalleByCompra(selected.getId());
        }
        
        return null;
    }
    
    public List<com.artemisa.domain.CompraRubro> getCompraRubroOfSelected()
    {
        if(selected != null)
        {
            return this.compraRubroServiceImpl.getCompraRubroByCompra(selected.getId());
        }
        
        return null;
    }
    
    public float getCompraTotal(long idCompra)
    {
        com.artemisa.domain.Compra compra = this.service.findOne(idCompra);
        float total = this.getProductosTotal(idCompra);
        
        total += compra.getIva();
        total += this.getRubrosTotal(idCompra);
        
        return total;
    }
    
    public float getProductosTotal(long idCompra)
    {
        float total = 0;
        
        List<com.artemisa.domain.CompraDetalle> items = this.compraDetalleServiceImpl.getCompraDetalleByCompra(idCompra);       
        
        if(items != null)
        {
            for(com.artemisa.domain.CompraDetalle c : items)
            {
                total += c.getCantidad() * c.getPrecio();
            }
        }
        
        return total;
    }
    
    public float getRubrosTotal(long idCompra)
    {
        float total = 0;
               
        List<com.artemisa.domain.CompraRubro> rubros = this.compraRubroServiceImpl.getCompraRubroByCompra(idCompra);
               
        if(rubros != null)
        {
            for(com.artemisa.domain.CompraRubro c : rubros)
            {
                switch(c.getRubro().getOperacion())
                {
                    case Rubro.SUMA:
                        total = (float) (total + c.getValor());
                        break;
                    case Rubro.RESTA:
                        total = (float) (total - c.getValor());
                        break;
                }
            }
        }
        
        return total;
    }
    
    public float getCantidadProductos(long idCompra)
    {
        float total = 0;
        
        List<com.artemisa.domain.CompraDetalle> items = this.compraDetalleServiceImpl.getCompraDetalleByCompra(idCompra);
        
        if(items != null)
        {
            for(com.artemisa.domain.CompraDetalle c : items)
            {
                total += c.getCantidad();
            }
        }
        
        return total;
    }
    
    public List<com.artemisa.domain.Compra> getList()
    {
        return this.service.list();
    }
    
    public void clean()
    {
        this.newCompra = new com.artemisa.domain.Compra();
    }
}
