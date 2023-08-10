package com.sym022.sym022.beans;

import com.sym022.sym022.entities.PermissionEntity;
import com.sym022.sym022.services.PermissionService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class PermissionBean extends FilterOfTable<PermissionEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private PermissionService permissionService = new PermissionService();
    private PermissionEntity permissionEntity = new PermissionEntity();
    private List<PermissionEntity> allPermissions;
    private List<PermissionEntity> allPermissionsSelected ;

    /*--- Method---*/

    /**
     * Method to have all the permissions in the select menu
     */
    public void initAllPermissions(){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allPermissions = permissionService.findPermissionAll(em);
            transaction.commit();
        }catch(Exception e){
            this.allPermissions = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /*---Getters and Setters---*/

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public PermissionEntity getPermissionEntity() {
        return permissionEntity;
    }

    public void setPermissionEntity(PermissionEntity permissionEntity) {
        this.permissionEntity = permissionEntity;
    }

    public List<PermissionEntity> getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(List<PermissionEntity> allPermissions) {
        this.allPermissions = allPermissions;
    }

    public List<PermissionEntity> getAllPermissionsSelected() {
        return allPermissionsSelected;
    }

    public void setAllPermissionsSelected(List<PermissionEntity> allPermissionsSelected) {
        this.allPermissionsSelected = allPermissionsSelected;
    }
}
