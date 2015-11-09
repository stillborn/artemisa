package com.artemisa.web.controller;

import com.artemisa.domain.Producto;
import com.artemisa.service.IBaseService;
import com.artemisa.service.ProductoServiceImpl;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.web.model.ProductoDataModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class ProductoController<Producto> extends BaseController implements Serializable
{
    private static final long serialVersionUID = -1747696151957059371L;
    
    private static final Log logger = LogFactory.getLog(ProductoController.class);
    
    @Autowired
    private ProductoServiceImpl service;
    
    private Producto selected;
    
    private Producto newProducto;
    
    private List<Producto> filter;
    
    private ProductoDataModel productoDataModel;
    
    public ProductoController() 
    {
        newProducto = (Producto) new com.artemisa.domain.Producto();
    }

    @PostConstruct
    public void Init()
    {
        productoDataModel = new ProductoDataModel(service);
    }
    
    public ProductoDataModel getProductoDataModel() {
        return productoDataModel;
    }

    public void setProductoDataModel(ProductoDataModel productoDataModel) {
        this.productoDataModel = productoDataModel;
    }
    
    public Producto getNewProducto() {
        return newProducto;
    }

    public void setNewProducto(Producto newProducto) {
        this.newProducto = newProducto;
    }

    public Producto getSelected() {
        return selected;
    }

    public void setSelected(Producto selected) {
        this.selected = selected;
    }

    public List<Producto> getFilter() {
        return filter;
    }

    public void setFilter(List<Producto> filter) {
        this.filter = filter;
    }
    
    public void create()
    {
        if(this.getNewProducto() != null)
        {
            try
            {
                this.service.save((com.artemisa.domain.Producto)this.getNewProducto());
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
            }
            catch(HibernateException | EntityExistsException ex )
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
                this.service.update((com.artemisa.domain.Producto)this.getSelected());
                
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
    
    public void delete(com.artemisa.domain.Producto entity)
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
        
    public void uploadProductoImage(FileUploadEvent event)
    {
        try
        {
            this.service.uploadProductImage(
                                    (com.artemisa.domain.Producto) this.selected, 
                                    event.getFile().getFileName(), 
                                    event.getFile().getInputstream()
                                );
            
            addMessage("Se ha cargado exitosamente el archivo!!", FacesMessage.SEVERITY_INFO);
        }
        catch(IOException ex)
        {
            addMessage("Se ha producido un error, intente de nuevo mas tarde!!", FacesMessage.SEVERITY_ERROR);
        }        
    }
    
    public List<com.artemisa.domain.Producto> getList()
    {
        return this.service.list();
    }
    
    public void generateCodigos()
    {
        List<com.artemisa.domain.Producto> prods = this.getList();
        try
        {
            for(com.artemisa.domain.Producto p : prods)
            {
                this.setSelected((Producto)p);
                this.update();
            }
        }
        catch(Exception ex)
        {
            addMessage("Se ha producido un error, intente de nuevo mas tarde!!", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public List<com.artemisa.domain.Producto> findByCodigo(String codigo)
    {
        return this.service.findByCodigo(codigo);
    }
    
    public void clean()
    {
        this.newProducto = (Producto) new com.artemisa.domain.Producto();
    }
}
