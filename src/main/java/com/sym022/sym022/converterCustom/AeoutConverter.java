package com.sym022.sym022.converterCustom;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.sym022.sym022.enums.Aeout;

@FacesConverter("AeoutConverter")
public class AeoutConverter implements Converter {
    @Override
    public Aeout getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Aeout.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Aeout aeout = (Aeout) value;
        return String.valueOf(aeout.getAeout());
    }
}
