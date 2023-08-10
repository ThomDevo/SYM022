package com.sym022.sym022.beans;

import com.sym022.sym022.entities.RoleEntity;
import com.sym022.sym022.entities.RolePermissionEntity;
import com.sym022.sym022.services.RolePermissionService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ManagedBean
@SessionScoped
public class RolePermissionBean extends FilterOfTable<RolePermissionEntity> implements Serializable {

    /*--- Variable declaration ---*/
    private RolePermissionService rolePermissionService = new RolePermissionService();
    private RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
    private List<RolePermissionEntity> allRolePermissions;
    private List<RolePermissionEntity> allRolePermissionsPerRole;
    @Inject
    private PermissionBean permissionsBean;
    @Inject
    private RoleBean rolesBean;

    /*---Method---*/

    /**
     * Method to initialize the list of permissions per role
     * @param role
     */
    public void initListOfPermissionsPerRole(RoleEntity role){
        EntityManager em = EMF.getEM();
        RolePermissionService rolePermissionService = new RolePermissionService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            allRolePermissionsPerRole = rolePermissionService.findRolePermissionByIdRole(role.getIdRole(),em);
            permissionsBean.setAllPermissionsSelected(allRolePermissionsPerRole.stream().map(RolePermissionEntity::getPermissionByIdPermission).collect(Collectors.toList()));
            transaction.commit();
        }catch(Exception e){
            allRolePermissionsPerRole = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /*--- Getters and Setters ---*/

    public RolePermissionService getRolePermissionService() {
        return rolePermissionService;
    }

    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    public RolePermissionEntity getRolePermissionEntity() {
        return rolePermissionEntity;
    }

    public void setRolePermissionEntity(RolePermissionEntity rolePermissionEntity) {
        this.rolePermissionEntity = rolePermissionEntity;
    }

    public List<RolePermissionEntity> getAllRolePermissions() {
        return allRolePermissions;
    }

    public void setAllRolePermissions(List<RolePermissionEntity> allRolePermissions) {
        this.allRolePermissions = allRolePermissions;
    }

    public List<RolePermissionEntity> getAllRolePermissionsPerRole() {
        return allRolePermissionsPerRole;
    }

    public void setAllRolePermissionsPerRole(List<RolePermissionEntity> allRolePermissionsPerRole) {
        this.allRolePermissionsPerRole = allRolePermissionsPerRole;
    }

    public PermissionBean getPermissionsBean() {
        return permissionsBean;
    }

    public void setPermissionsBean(PermissionBean permissionsBean) {
        this.permissionsBean = permissionsBean;
    }

    public RoleBean getRolesBean() {
        return rolesBean;
    }

    public void setRolesBean(RoleBean rolesBean) {
        this.rolesBean = rolesBean;
    }
}
