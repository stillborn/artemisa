/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.service;

import com.artemisa.dao.BaseDao;
import com.artemisa.domain.Compra;
import com.artemisa.domain.Parametro;
import com.artemisa.domain.Producto;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.util.upload.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasrubiano
 */
@Service()
public class ProductoServiceImpl implements Serializable, IBaseService<com.artemisa.domain.Producto>
{
    private static final long serialVersionUID = -4785143675860746102L;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Producto> service;
    
    @Autowired
    private BaseDao<com.artemisa.domain.Parametro> parametroService;
    
    @PostConstruct
    public void PostConstruct()
    {
        this.service.setType(com.artemisa.domain.Producto.class);
        this.parametroService.setType(com.artemisa.domain.Parametro.class);
    }
    
    /**
     *
     * @param entity
     * @return
     * @throws com.artemisa.service.exceptions.EntityExistsException
     */
    @Override
    public Producto save(com.artemisa.domain.Producto entity) throws EntityExistsException, HibernateException
    {
        if(this.service.findByField("codigo", entity.getCodigo()) == null)
        {
            entity.setCodigo("-");
            entity.setFoto("-");
            Producto m = this.service.save(entity);
            
            m.setCodigo(makeProductoCodigo(m));
            m = this.service.update(m);
            
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("La marca con el código " + entity.getCodigo() + " ya existe!");
        }
    }
    
    @Override
    public Producto update(com.artemisa.domain.Producto entity)  throws EntityExistsException, HibernateException{
        
        entity.setCodigo(makeProductoCodigo(entity));
        
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "codigo = '" + entity.getCodigo() + "'");
        conditions.put("and", "id != " + entity.getId());
        
        List<com.artemisa.domain.Producto> foundProductos = this.service.find(conditions);
        
        if(foundProductos == null)
        {
            Producto m = this.service.update(entity);
            return m;
        }
        else
        {
            throw new com.artemisa.service.exceptions.EntityExistsException("El producto con el código " + entity.getCodigo() + " ya existe!");
        }
    }
    
    @Override
    public void delete(com.artemisa.domain.Producto entity) throws HibernateException{
        if(entity != null)
        {
            Parametro path = parametroService.findByField("nombre", com.artemisa.dao.ParametroDao.UPLOAD_PATH);
            
            this.service.delete(entity);
            if(entity.getFoto() != null)
            {
                try {
                    File.delete(path.getValor(), entity.getFoto());
                } catch (IOException ex) {
                    Logger.getLogger(ProductoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public List<com.artemisa.domain.Producto> list()
    {
        return this.service.find("codigo");
    }
    
    public void uploadProductImage(com.artemisa.domain.Producto producto, String fileName, InputStream stream) throws IOException
    {
        String ext = FilenameUtils.getExtension(fileName);
        String uniqueName = UUID.randomUUID().toString();
        
        Parametro path = parametroService.findByField("nombre", com.artemisa.dao.ParametroDao.UPLOAD_PATH);
        
        try {
            String name = uniqueName+"."+ext;
            
            File.upload(path.getValor(), name, stream);
            producto.setFoto(name);
            
            this.service.update(producto);
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }
    
    public String makeProductoCodigo(Producto producto)
    {
        String taxonomy = "{marca}_{tipo_producto}_{desc}_{presentacion}";
        int maxLength = 0 ;
        
        if(producto.getMarca() != null)
        {
            maxLength  =  producto.getMarca().getNombre().trim().length();
            taxonomy = taxonomy.replace("{marca}", producto.getMarca()
                    .getNombre().trim()
                    .substring(0, maxLength).toUpperCase());
        }
        
        if(producto.getTipoProducto() != null)
        {
            maxLength  =  producto.getTipoProducto().getNombre().trim().length();
            taxonomy = taxonomy.replace("{tipo_producto}", producto.getTipoProducto()
                    .getNombre()
                    .trim().replace(' ', '_')
                    .substring(0,maxLength).toUpperCase());
        }
        
        if(producto.getDescripcion() != null)
        {
            maxLength = producto.getDescripcion().trim().length();
            taxonomy = taxonomy.replace("{desc}", producto.getDescripcion()
                    .trim().replace(' ', '_')
                    .substring(0,maxLength).toUpperCase());
        }
        
        if(producto.getPresentacion() != null)
        {
            String p = producto.getPresentacion().getDescripcion().replaceAll("\\s","");
            maxLength = p.length();
            taxonomy = taxonomy.replace("{presentacion}", p.substring(0, maxLength).toUpperCase());
        }
        
        return taxonomy;
    }
    
    @Override
    public Producto findOne(long id) throws HibernateException {
        return  this.service.find(id);
    }
    
    public List<Producto> findByCodigo(String codigo)
    {
        Map<String, String> conditions = new TreeMap<>();
        conditions.put("", "codigo like '%" + codigo + "%'");
        
        return this.service.find(conditions, "codigo");
    }
    
    public List<Producto> find(Map<String, String> filters, String order,int startIndex, int maxResults) throws HibernateException 
    {
        return this.service.find(filters, order, startIndex, maxResults);
    }
    
    public Long count(Map<String, String> conditions)
    {
        return this.service.count(conditions);
    }
    
    public Long count()
    {
        return this.service.count();
    }
}
