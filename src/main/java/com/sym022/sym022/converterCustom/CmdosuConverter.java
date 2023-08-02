package com.sym022.sym022.converterCustom;
import com.sym022.sym022.enums.Cmdosu;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

@FacesConverter("CmdosuConverter")
public class CmdosuConverter implements Converter {

    @Override
    public Cmdosu getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Cmdosu.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Cmdosu cmdosu = (Cmdosu) value;
        return String.valueOf(cmdosu.getCmdosu());
    }
}
