package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Aerel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("AerelConverter")
public class AerelConverter implements Converter {
    @Override
    public Aerel getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Aerel.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Aerel aerel = (Aerel) value;
        return String.valueOf(aerel.getAerel());
    }
}
