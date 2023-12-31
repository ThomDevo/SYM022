package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.FormLabel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("FormLabelConverter")
public class FormLabelConverter implements Converter {

    @Override
    public FormLabel getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return FormLabel.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        FormLabel formLabel = (FormLabel) value;
        return String.valueOf(formLabel.getFormLabel());
    }
}
