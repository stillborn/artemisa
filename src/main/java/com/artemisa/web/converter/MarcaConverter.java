/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.web.converter;

import com.artemisa.domain.Marca;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author nicolasrubiano
 */
@FacesConverter("MarcaConverter")
public class MarcaConverter extends GenericEntityConverter<Marca>
{

    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return null;
    }
}
