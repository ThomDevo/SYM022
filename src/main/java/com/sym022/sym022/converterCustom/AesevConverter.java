package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Aesev;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("AesevConverter")
public class AesevConverter implements Converter {
    @Override
    public Aesev getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Aesev.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Aesev aesev = (Aesev) value;
        return String.valueOf(aesev.getAesev());
    }
}
