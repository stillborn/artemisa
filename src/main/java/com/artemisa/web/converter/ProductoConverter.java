/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.artemisa.web.converter;

import com.artemisa.domain.Producto;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author nicolasrubiano
 */
@Component()
@FacesConverter("ProductoConverter")
public class ProductoConverter extends GenericEntityConverter<Producto>
{
    
    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return (IBaseService)FacesContextUtils.getWebApplicationContext(tx).getBean("productoServiceImpl"); 
    }
}
