package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Culture;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("CultureConverter")
public class CultureConverter implements Converter {

    @Override
    public Culture getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Culture.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Culture culture = (Culture) value;
        return String.valueOf(culture.getCulture());
    }
}
