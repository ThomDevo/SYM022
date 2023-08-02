package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.VisitLabel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("VisitLabelConverter")
public class VisitLabelConverter implements Converter {

    @Override
    public VisitLabel getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return VisitLabel.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        VisitLabel visitLabel = (VisitLabel) value;
        return String.valueOf(visitLabel.getVisitLabel());
    }
}
