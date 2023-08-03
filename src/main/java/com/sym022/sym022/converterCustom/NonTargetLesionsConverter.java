package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.NonTargetLesions;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("NonTargetLesionsConverter")
public class NonTargetLesionsConverter implements Converter {

    @Override
    public NonTargetLesions getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return NonTargetLesions.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        NonTargetLesions nonTargetLesions = (NonTargetLesions) value;
        return String.valueOf(nonTargetLesions.getNonTargetLesions());
    }

}
