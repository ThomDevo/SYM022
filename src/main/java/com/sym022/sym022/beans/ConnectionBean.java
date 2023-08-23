package com.sym022.sym022.beans;

import com.sym022.sym022.entities.UserEntity;
import com.sym022.sym022.exceptions.ConnectionUserException;
import com.sym022.sym022.services.RolePermissionService;
import com.sym022.sym022.services.UserService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean extends FilterOfTable<UserEntity> implements Serializable {

    private UserEntity user;
    private UserEntity userConnected = new UserEntity();
    private String messageErrorConnection ="hidden";
    private String password;

    /**
     * Method to logout
     * @return Connection.xhtml
     */
    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/Connection.xhtml?faces-redirect=true";
    }

    public String connection()
    {
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        RolePermissionService rolePermissionService = new RolePermissionService();
        String redirect;

        this.user = new UserEntity();

        try
        {
            this.userConnected = userService.findUserByUsername(this.userConnected.getUsername(), em);
            //checkUserConnection(this.userConnected, this.password);
            this.userConnected.listOfPermissions = rolePermissionService.findRolePermissionByIdRole(this.userConnected.getRoleByIdRole().getIdRole(), em);
            //ProcessUtils.debug(String.valueOf(this.userForm.listOfPermissions.size()));
            this.user = userConnected;
            this.messageErrorConnection = "hidden";
            redirect = "/VIEW/home";
            //ProcessUtils.debug(String.valueOf(user.getIdUser()));
        }
        catch(Exception e)
        {
            ProcessUtils.debug(" I'm in the catch of the connection method: " + e);

            //Put an error message
            this.messageErrorConnection = "";
            redirect = "Connection";
        }
        finally
        {
            em.close();
        }

        return redirect;
    }

    /**
     * Initialize list RolePermission
     */
    public static void initListPermissionRole(UserEntity user)
    {
        EntityManager em = EMF.getEM();
        RolePermissionService permissionroleService = new RolePermissionService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "Permissionrole" entity
            user.listOfPermissions = permissionroleService.findRolePermissionByIdRole(user.getRoleByIdRole().getIdRole(), em);
            transaction.commit();
        }
        catch(Exception e)
        {
            ProcessUtils.debug(" je suis dans le catch de l'initialization du rolePermission : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }

    /**
     * Method to control the permissions of the user
     * @param permissionName
     * @return
     */
    //ask is user log has permissions send.
    public boolean verifyPermissionUser(String permissionName){
        if(this.user == null || this.user.getIdUser()==0)
            return false;
        return this.user.verifyPermission(permissionName);
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }


    /**
     * User processing method
     */
    public void checkUserConnection (UserEntity userRequest, String password) throws ConnectionUserException
    {
        if (! (ProcessUtils.checkPassword(password,userRequest)
                && userRequest.getStatus()))
        {
            throw new ConnectionUserException();
        }
    }

    /*---Getters and Setters ---*/

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getMessageErrorConnection() {
        String message = this.messageErrorConnection;
        this.messageErrorConnection = "hidden";
        return message;
    }
    public void setMessageErrorConnection(String messageErrorConnection) {
        this.messageErrorConnection = messageErrorConnection;
    }

    public UserEntity getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(UserEntity userConnected) {
        this.userConnected = userConnected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
