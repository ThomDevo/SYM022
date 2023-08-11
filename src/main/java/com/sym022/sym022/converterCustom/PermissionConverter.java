package com.sym022.sym022.converterCustom;

import com.sym022.sym022.entities.PermissionEntity;
import com.sym022.sym022.services.PermissionService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PermissionConverter")
public class PermissionConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();
        PermissionEntity permission = null;
        try {
            permission = permissionService.findPermissionById(Integer.parseInt(value), em);
        } catch (Exception e) {
            ProcessUtils.debug(""+e);
        } finally {
            em.close();
        }
        return permission;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        PermissionEntity permission = (PermissionEntity) value;
        return String.valueOf(permission.getIdPermission());
    }
}
