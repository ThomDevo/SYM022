package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.RoleLabel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("RoleLabelConverter")
public class RoleLabelConverter implements Converter {

    @Override
    public RoleLabel getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return RoleLabel.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        RoleLabel roleLabel = (RoleLabel) value;
        return String.valueOf(roleLabel.getRoleLabel());
    }
}
