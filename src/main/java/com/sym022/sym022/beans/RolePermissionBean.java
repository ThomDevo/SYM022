package com.sym022.sym022.beans;

import com.sym022.sym022.entities.RoleEntity;
import com.sym022.sym022.entities.RolePermissionEntity;
import com.sym022.sym022.services.RolePermissionService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Named
@ManagedBean
@SessionScoped
public class RolePermissionBean extends FilterOfTable<RolePermissionEntity> implements Serializable {

    /*--- Variable declaration ---*/
    private RolePermissionService rolePermissionService = new RolePermissionService();
    private RolePermissionEntity rolePermission = new RolePermissionEntity();
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

    /**
     * Method to have all the role permissions
     */
    public void allRolePermissions(){
        EntityManager em = EMF.getEM();
        RolePermissionService rolePermissionService = new RolePermissionService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            filterOfTable = rolePermissionService.findAll(this.filter,em);
            transaction.commit();
        }catch(Exception e){
            filterOfTable = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to add a list of role permissions for a role in the DB
     * @return a role Permission
     */
    public String submitFormAddRolePermissions(){

        //ProcessUtils.debug("Submit form add role permissions"+permissionsBean.getAllPermissionsSelected().size());
        //ProcessUtils.debug("Submit form list permissions"+permissionsBean.getAllPermissions().size());
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RolePermissionService rolePermissionService = new RolePermissionService();
        EntityTransaction transaction = em.getTransaction();
        RolePermissionEntity rolePermissionEntity;
        try{
            ProcessUtils.debug("Begin ");
            transaction.begin();
            for(int i=0;i< permissionsBean.getAllPermissionsSelected().size();i++){
                rolePermissionEntity = new RolePermissionEntity();
                //ProcessUtils.debug("A "+ rolesBean.getRole());
                rolePermissionEntity.setRoleByIdRole(rolesBean.getRole());
                //ProcessUtils.debug("B "+rolePermissionEntity.getRolesByIdRole());
                rolePermissionEntity.setPermissionByIdPermission(permissionsBean.getAllPermissionsSelected().get(i));
                //ProcessUtils.debug("C " + rolePermissionEntity.getPermissionsByIdPermission());
                rolePermissionService.addRolePermission(rolePermissionEntity,em);
                //ProcessUtils.debug("D ");
            }

            transaction.commit();

        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String listOfPermissionsOf = bundle.getString("listOfPermissionsOf");
        String add = bundle.getString("add");
        addMessage(listOfPermissionsOf+" "+ add,"Confirmation");
        return redirect;
    }

    /**
     * Method to update a list of role permissions for a role in the DB
     * @return a role Permission
     */
    public String submitFormUpdateRolePermissions(){

        //ProcessUtils.debug("Submit form add role permissions"+permissionsBean.getAllPermissionsSelected().size());
        //ProcessUtils.debug("Submit form list permissions"+permissionsBean.getAllPermissions().size());
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RolePermissionService rolePermissionService = new RolePermissionService();
        EntityTransaction transaction = em.getTransaction();
        RolePermissionEntity rolePermissionEntity;
        boolean findMatch;

        try{
            //ProcessUtils.debug("Begin ");
            transaction.begin();
            for(int i =0 ; i< permissionsBean.getAllPermissionsSelected().size(); i++){
                findMatch = false;
                for(int j= 0; j < allRolePermissionsPerRole.size(); j++){
                    if(permissionsBean.getAllPermissionsSelected().get(i).getIdPermission() == allRolePermissionsPerRole.get(j).getPermissionByIdPermission().getIdPermission()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    rolePermissionEntity = new RolePermissionEntity();
                    //ProcessUtils.debug("A "+ rolesBean.getRole());
                    rolePermissionEntity.setRoleByIdRole(this.rolePermission.getRoleByIdRole());
                    //ProcessUtils.debug("B "+rolePermissionEntity.getRolesByIdRole());
                    rolePermissionEntity.setPermissionByIdPermission(permissionsBean.getAllPermissionsSelected().get(i));
                    //ProcessUtils.debug("C " + rolePermissionEntity.getPermissionsByIdPermission());
                    rolePermissionService.addRolePermission(rolePermissionEntity,em);
                }
            }

            for(int j =0 ; j< allRolePermissionsPerRole.size(); j++){
                findMatch = false;
                for(int i= 0; i < permissionsBean.getAllPermissionsSelected().size(); i++){
                    if(permissionsBean.getAllPermissionsSelected().get(i).getIdPermission() == allRolePermissionsPerRole.get(j).getPermissionByIdPermission().getIdPermission()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    rolePermissionEntity = rolePermissionService.findRolePermissionByIdRoleAndByIdPermission(getRolePermissionEntity().getRoleByIdRole().getIdRole(), allRolePermissionsPerRole.get(j).getPermissionByIdPermission().getIdPermission(),em);
                    rolePermissionService.deleteRolePermission(rolePermissionEntity,em);
                }
            }
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String listOfPermissionsOf = bundle.getString("listOfPermissionsOf");
        String update = bundle.getString("update");
        addMessage(listOfPermissionsOf+" "+ update,"Confirmation");
        return redirect;
    }

    /**
     * Method to have I18n messages in Back-end
     * @param summary
     * @param detail
     */
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    /*--- Getters and Setters ---*/

    public RolePermissionService getRolePermissionService() {
        return rolePermissionService;
    }

    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    public RolePermissionEntity getRolePermissionEntity() {
        return rolePermission;
    }

    public void setRolePermissionEntity(RolePermissionEntity rolePermissionEntity) {
        this.rolePermission = rolePermissionEntity;
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

    public RolePermissionEntity getRolePermission() {
        return rolePermission;
    }

    public void setRolePermission(RolePermissionEntity rolePermission) {
        this.rolePermission = rolePermission;
    }
}
