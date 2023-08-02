package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Cmroute;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("CmrouteConverter")
public class CmrouteConverter implements Converter {

    @Override
    public Cmroute getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Cmroute.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Cmroute cmroute = (Cmroute) value;
        return String.valueOf(cmroute.getCmroute());
    }

}
