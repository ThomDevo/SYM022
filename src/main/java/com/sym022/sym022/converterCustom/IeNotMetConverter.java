package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.IeNotMet;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("IeNotMetConverter")
public class IeNotMetConverter implements Converter {

    @Override
    public IeNotMet getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return IeNotMet.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        IeNotMet ieNotMet = (IeNotMet) value;
        return String.valueOf(ieNotMet.getIeNotMet());
    }
}


