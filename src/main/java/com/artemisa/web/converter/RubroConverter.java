/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemisa.web.converter;

import com.artemisa.dao.BaseDao;
import com.artemisa.dao.RubroDao;
import com.artemisa.domain.Rubro;
import com.artemisa.service.IBaseService;
import com.artemisa.web.generic.GenericEntityConverter;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nicolasrubiano
 */
@FacesConverter("RubroConverter")
public class RubroConverter extends GenericEntityConverter<Rubro>
{
    @Override
    public IBaseService getBaseService(FacesContext tx) {
        return null;
    }
}
