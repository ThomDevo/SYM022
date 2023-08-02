package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.Ethnicity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("EthnicityConverter")
public class EthnicityConverter implements Converter {

    @Override
    public Ethnicity getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Ethnicity.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Ethnicity ethnicity = (Ethnicity) value;
        return String.valueOf(ethnicity.getEthnicity());
    }
}
