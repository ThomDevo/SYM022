package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.TempRoute;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("TempRouteConverter")
public class TempRouteConverter implements Converter {
    @Override
    public TempRoute getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return TempRoute.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        TempRoute tempRoute = (TempRoute) value;
        return String.valueOf(tempRoute.getTempRoute());
    }
}
