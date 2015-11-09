/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.web.converter;

import com.artemisa.domain.Proveedor;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author nicolasrubiano
 */
@FacesConverter("ProveedorConverter")
public class ProveedorConverter extends GenericEntityConverter<Proveedor>
{

    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return null;
    }
}
