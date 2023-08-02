package com.sym022.sym022.converterCustom;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.sym022.sym022.enums.Aetoxgd;

@FacesConverter("AetoxgdConverter")
public class AetoxgdConverter implements Converter {
    @Override
    public Aetoxgd getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Aetoxgd.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Aetoxgd aetoxgd = (Aetoxgd) value;
        return String.valueOf(aetoxgd.getAetoxgd());
    }
}
