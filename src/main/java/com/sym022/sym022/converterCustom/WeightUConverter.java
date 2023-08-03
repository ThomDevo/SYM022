package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.WeightU;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("WeightUConverter")
public class WeightUConverter implements Converter {


    @Override
    public WeightU getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return WeightU.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        WeightU weightU = (WeightU) value;
        return String.valueOf(weightU.getWeightU());
    }
}
