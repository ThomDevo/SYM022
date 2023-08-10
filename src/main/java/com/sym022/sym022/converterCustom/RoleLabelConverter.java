package com.sym022.sym022.converterCustom;

import com.sym022.sym022.entities.RoleEntity;
import com.sym022.sym022.services.RoleService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.ProcessUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("RoleLabelConverter")
public class RoleLabelConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        RoleEntity role = null;
        try{
            role = roleService.findRoleById(Integer.parseInt(value),em);
        }catch(Exception e){
            ProcessUtils.debug(""+e);
        }finally{
            em.close();
        }
        return role;
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        RoleEntity role = (RoleEntity) value;
        return String.valueOf(role.getIdRole());
    }
}
