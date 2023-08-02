package com.sym022.sym022.converterCustom;
import com.sym022.sym022.enums.Cmfreq;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("CmfreqConverter")
public class CmfreqConverter implements Converter{
    @Override
    public Cmfreq getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Cmfreq.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Cmfreq cmfreq = (Cmfreq) value;
        return String.valueOf(cmfreq.getCmfreq());
    }

}
