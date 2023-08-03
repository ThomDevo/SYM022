package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.HeightU;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("HeightUConverter")
public class HeightUConverter implements Converter {


    @Override
    public HeightU getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return HeightU.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        HeightU heightU = (HeightU) value;
        return String.valueOf(heightU.getHeightU());
    }
}
