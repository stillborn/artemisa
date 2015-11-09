/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.web.converter;

import com.artemisa.domain.Presentacion;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author nicolasrubiano
 */
@FacesConverter("PresentacionConverter")
public class PresentacionConverter extends GenericEntityConverter<Presentacion>
{
    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return null;
    }
}
