package com.sym022.sym022.converterCustom;

import com.sym022.sym022.enums.QueryStatus;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("QueryStatusConverter")
public class QueryStatusConverter implements Converter {

    @Override
    public QueryStatus getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return QueryStatus.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        QueryStatus queryStatus = (QueryStatus) value;
        return String.valueOf(queryStatus.getQueryStatus());
    }
}
