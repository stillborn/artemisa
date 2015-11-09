/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.web.converter;

import com.artemisa.dao.BaseDao;
import com.artemisa.dao.TipoProductoDao;
import com.artemisa.domain.TipoProducto;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nicolasrubiano
 */
@FacesConverter("TipoProductoConverter")
public class TipoProductoConverter extends GenericEntityConverter<TipoProducto>
{
    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return null;
    }
}
