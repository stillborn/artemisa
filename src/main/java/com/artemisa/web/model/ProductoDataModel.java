package com.artemisa.web.model;

import com.artemisa.domain.Producto;
import com.artemisa.service.ProductoServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

import org.primefaces.model.LazyDataModel;

public class ProductoDataModel extends LazyDataModel<Producto>
{
    private static final long serialVersionUID = 6247811718270143900L;
    
    private ProductoServiceImpl service;
    
    public ProductoDataModel(ProductoServiceImpl service)
    {
        this.service = service;
    }
    
    @Override
    public List<Producto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        
        Map<String, String> filter = new HashMap<>();
        
        int count = 0;
        String condition = "";
        for (Map.Entry<String, Object> entry : filters.entrySet())
        {
            condition = entry.getKey() + " like '%" + entry.getValue() + "%'";
            if(count == 0)
            {
                filter.put("", condition);
            }
            else
            {
                filter.put("AND", condition);
            }  
            
            count++;
        }
        
        List<Producto> data = service.find(filter, "tipoProducto.nombre, marca.nombre, presentacion.descripcion, descripcion" ,first, pageSize);
 
        //rowCount
        int dataSize = service.count(filter).intValue();
        this.setRowCount(dataSize);
 
       return data;
    }
}
