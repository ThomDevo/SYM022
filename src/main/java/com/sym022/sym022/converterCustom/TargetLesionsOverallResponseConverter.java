package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.TargetLesionsOverallResponse;
import javax.faces.convert.Converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("TargetLesionsOverallResponseConverter")
public class TargetLesionsOverallResponseConverter implements Converter {

    @Override
    public TargetLesionsOverallResponse getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return TargetLesionsOverallResponse.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        TargetLesionsOverallResponse targetLesionsOverallResponse = (TargetLesionsOverallResponse) value;
        return String.valueOf(targetLesionsOverallResponse.getTargetLesionsOverallResponse());
    }
}
