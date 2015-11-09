package com.artemisa.web.generic;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.artemisa.domain.BaseEntity;
import com.artemisa.service.IBaseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;


@Named
public abstract class GenericEntityConverter<T extends BaseEntity> implements Converter {
    
    private static final Log logger = LogFactory.getLog(GenericEntityConverter.class);
    
    public abstract  IBaseService getBaseService(FacesContext tx);

    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        // Special handling for Primefaces PickList
        if (component instanceof PickList) {
            @SuppressWarnings({"unchecked"})
                    DualListModel<BaseEntity> dl = (DualListModel<BaseEntity>) ((PickList) component).getValue();
            for (BaseEntity e : dl.getSource()) {
                if (Long.valueOf(value).equals(e.getId())) {
                    return e;
                }
            }
            for (BaseEntity e : dl.getTarget()) {
                if (Long.valueOf(value).equals(e.getId())) {
                    return e;
                }
            }
            // TODO Handle not found
        }
        
        if (component instanceof SelectOneMenu) {
            List<UIComponent> childs = component.getChildren();
            UISelectItems items = null;
            if (childs != null) {
                for (UIComponent ui : childs) {
                    if (ui instanceof UISelectItems) {
                        items = (UISelectItems) ui;
                        break;
                    } else if (ui instanceof UISelectItem) {
                        UISelectItem item = (UISelectItem) ui;
                        try {
                            BaseEntity val = (BaseEntity) item.getItemValue();
                            if (value.equals(val.getId().toString())) {
                                return val;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (items != null) {
                List<BaseEntity> values = (List<BaseEntity>) items.getValue();
                if (values != null) {
                    for (BaseEntity val : values) {
                        if (value.equals(val.getId().toString())) {
                            return val;
                        }
                    }
                }
            }
        }
        
        return getBaseService(context).findOne(Long.valueOf(value));
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        
        if (value == null || !(value instanceof BaseEntity)) {
            logger.debug("Can not convert value: " + value);
            return "";
        }
        
        Long id = ((BaseEntity) value).getId();
        return id != null ? id.toString() : null;
    }
    
}
