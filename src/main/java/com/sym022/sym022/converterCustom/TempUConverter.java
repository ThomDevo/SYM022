package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.TempU;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("TempUConverter")
public class TempUConverter implements Converter {

    @Override
    public TempU getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return TempU.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        TempU tempU = (TempU) value;
        return String.valueOf(tempU.getTempU());
    }
}
