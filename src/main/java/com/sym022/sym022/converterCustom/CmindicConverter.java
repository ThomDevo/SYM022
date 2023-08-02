package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Cmindic;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("CmindicConverter")
public class CmindicConverter implements Converter{

    @Override
    public Cmindic getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Cmindic.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Cmindic cmindic = (Cmindic) value;
        return String.valueOf(cmindic.getCmindic());
    }

}
