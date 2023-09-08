package com.sym022.sym022.beans;

import com.sym022.sym022.entities.RoleEntity;
import com.sym022.sym022.services.RoleService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class RoleBean extends FilterOfTable<RoleEntity> implements Serializable {

    /*--- Variable declaration ---*/
    private RoleEntity role = new RoleEntity();
    private RoleService roleService = new RoleService();
    private String buttonSuccess = "false";
    private List<RoleEntity> allRole;
    private List<RoleEntity> allRoleEmptyPermission;
    private String messageErrorRoleName = "hidden";
    private String messageErrorRoleNameLabel = "hidden";

    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormRole();
        return redirect;
    }

    /**
     * Method to reset the form to add or update a role
     */
    public void initFormRole(){
        this.role.setRoleLabel("");
        this.messageErrorRoleName = "hidden";
    }


    /**
     * Method to have all roles in the select Menu
     */
    public void initAllEditor(){
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        try{
            this.allRole = roleService.findRoleAll(em);
        }catch(Exception e){
            this.allRole = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    /**
     * Method to test the roleLabel empty in front end
     * @return messageErrorRoleName hidden or not and button create/update deactivate or not
     */
    public String testRoleLabel(){
        String redirect = "null";
        if(role.getRoleLabel().length() < 2 || role.getRoleLabel().length() > 200){
            this.messageErrorRoleNameLabel = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorRoleNameLabel = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to find all roles who have no permission in the select Menu
     */
    public void initAllRoleEmptyPermissions(){
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        try{
            this.allRoleEmptyPermission = roleService.findRoleAllEmptyPermissions(em);
        }catch(Exception e){
            this.allRoleEmptyPermission = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    /**
     * Method to filter the roles on roleLabel
     */
    public void researchFilterRole(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = roleService.findRoleByFilter(this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }
    }

    /**
     * Method to add a new role in the DB
     * @return a role
     */
    public String submitFormAddRole() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RoleService roleService = new RoleService();
        EntityTransaction transaction = em.getTransaction();

        if(role.getRoleLabel().length() < 2 || role.getRoleLabel().length() > 200){
            this.messageErrorRoleName = "hidden";
            this.messageErrorRoleNameLabel = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                role.setRoleLabel(role.getRoleLabel().toUpperCase());
                transaction.begin();
                if(roleService.isRoleExist(role.getRoleLabel(), em)){
                    this.messageErrorRoleName = "";
                    this.messageErrorRoleNameLabel = "hidden";
                    redirect = "null" ;
                    return redirect;
                }
                roleService.addRole(role,em);
                transaction.commit();
                confirmAddRole();
                initFormRole();
            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addRole method: "+ e);
                redirect = "null" ;
            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
        }

        return redirect;
    }

    /**
     * Method to update a role in the DB
     * @return a role
     */
    public String submitFormUpdateRole(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RoleService roleService = new RoleService();
        EntityTransaction transaction = em.getTransaction();

        if(role.getRoleLabel().length() < 2 || role.getRoleLabel().length() > 200){
            this.messageErrorRoleName = "hidden";
            this.messageErrorRoleNameLabel = "";
            redirect = "null";
            return redirect;
        }else {
            try {
                role.setRoleLabel(role.getRoleLabel().toUpperCase());
                transaction.begin();
                if (roleService.isRoleExist(role.getRoleLabel(), em)) {
                    this.messageErrorRoleName = "";
                    redirect = "null";
                    return redirect;
                }
                roleService.updateRole(role, em);
                transaction.commit();
                confirmUpdateRole();
                initFormRole();
            } catch (Exception e) {
                ProcessUtils.debug(" I'm in the catch of the updateRole method: " + e);
                redirect = "null";
                return redirect;
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                em.close();
            }
        }
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

    /**
     * Method for getting a popup confirming that the role has been added
     */
    public void confirmAddRole(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addRole = bundle.getString("role");
        String add = bundle.getString("add");

        addMessage(addRole+" "+role.getRoleLabel()+" "+add,"Confirmation");
    }

    /**
     * Method for getting a popup confirming that the role has been updated
     */
    public void confirmUpdateRole(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String updateRole = bundle.getString("role");
        String update = bundle.getString("update");

        addMessage(updateRole+" "+role.getRoleLabel()+" "+update,"Confirmation");
    }

    /*--- Getters and Setters ---*/

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public List<RoleEntity> getAllRole(){
        return this.allRole;
    }

    public void setAllRole(List<RoleEntity> allRole) {
        this.allRole = allRole;
    }

    public List<RoleEntity> getAllRoleEmptyPermission() {
        return allRoleEmptyPermission;
    }

    public void setAllRoleEmptyPermission(List<RoleEntity> allRoleEmptyPermission) {
        this.allRoleEmptyPermission = allRoleEmptyPermission;
    }

    public String getMessageErrorRoleName() {
        return messageErrorRoleName;
    }

    public void setMessageErrorRoleName(String messageErrorRoleName) {
        this.messageErrorRoleName = messageErrorRoleName;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrorRoleNameLabel() {
        return messageErrorRoleNameLabel;
    }

    public void setMessageErrorRoleNameLabel(String messageErrorRoleNameLabel) {
        this.messageErrorRoleNameLabel = messageErrorRoleNameLabel;
    }
}
