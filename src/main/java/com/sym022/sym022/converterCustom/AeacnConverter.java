package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Aeacn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("AeacnConverter")
public class AeacnConverter implements Converter {
    @Override
    public Aeacn getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Aeacn.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Aeacn certificateType = (Aeacn) value;
        return String.valueOf(certificateType.getAeacn());
    }
}
