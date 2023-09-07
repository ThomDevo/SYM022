package com.sym022.sym022.beans;

import com.sym022.sym022.entities.UserEntity;
import com.sym022.sym022.services.SiteService;
import com.sym022.sym022.services.UserService;
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
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class UserBean extends FilterOfTable<UserEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private UserEntity user = new UserEntity();
    private UserService userService = new UserService();
    private List<UserEntity> allUsers;
    private List<UserEntity> allUsersEmptySites;
    private String messageErrorUserName = "hidden";

    /*--- Methods ---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormUser();
        return redirect;
    }

    /**
     * Method to find all the users who have no sites in the select menu
     */
    public void initAllUserAllEmptySites(){
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        try{
            this.allUsersEmptySites = userService.findUserAllEmptySites(em);
        }catch(Exception e){
            this.allUsersEmptySites = new ArrayList<>();
        }finally{
            em.close();
        }
    }


    /**
     * Method to filter the users on the lastname, the firstname, the mail and the role
     */
    public void researchFilterUser(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = userService.findUserByFilterAndOrderAsc(this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }
    }

    /**
     * Method to add a new user in the DB
     * @return a user
     */
    public String submitFormAddUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        UserService userService = new UserService();

        try{
            user.setFirstName(user.getFirstName().substring(0,1).toUpperCase()+user.getFirstName().substring(1).toLowerCase());
            user.setLastName(user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());
            user.setPassword(ProcessUtils.cryptPassword(user.getPassword()));
            user.setStatus(true);

            transaction.begin();
            initErrorMessageUser();
            if(userService.isUserExist(user.getUsername(),em)){
                this.messageErrorUserName="";
                redirect = "null" ;
                return redirect;
            }
            userService.addUser(user,em);
            transaction.commit();
            confirmAddUser();
            initFormUser();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addUser method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    /**
     * Method to update a user in the DB
     * @return a user
     */
    public String submitFormUpdateUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        UserService userService = new UserService();

        try{
            user.setFirstName(user.getFirstName().substring(0,1).toUpperCase()+user.getFirstName().substring(1).toLowerCase());
            user.setLastName(user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());

            transaction.begin();
            initErrorMessageUser();
            userService.updateUser(user,em);
            transaction.commit();
            confirmUpdateUser();
            initFormUser();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the updateUser method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    /**
     * Method to reset the form to add or update a user
     */
    public void initFormUser(){
        this.user.setUsername("");
        this.user.setPassword("");
        this.user.setLastName("");
        this.user.setFirstName("");
        this.user.setMail("");
        this.user.setStatus(true);
        initErrorMessageUser();
    }

    /**
     * Method to reset all the errors messages in the form for add or update a user
     */
    public void initErrorMessageUser(){
        this.messageErrorUserName = "hidden";
    }

    /**
     * Method to quit the page add or update a user and reset the form
     * @return home Page
     */
    public String cancelFormUser(){
        String redirect = "/VIEW/home";
        initFormUser();
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
     * Method for getting a popup confirming that the user has been added
     */
    public void confirmAddUser(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addUser = bundle.getString("user");
        String add = bundle.getString("add");

        addMessage(addUser+" "+user.getLastName()+" "+user.getFirstName()+" "+add,"Confirmation");
    }

    /**
     * Method for getting a popup confirming that the user has been updated
     */
    public void confirmUpdateUser(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String updateUser = bundle.getString("user");
        String update = bundle.getString("update");

        addMessage(updateUser+" "+user.getLastName()+" "+user.getFirstName()+" "+update,"Confirmation");
    }

    /*--- Getters and Setters ---*/

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getMessageErrorUserName() {
        return messageErrorUserName;
    }

    public void setMessageErrorUserName(String messageErrorUserName) {
        this.messageErrorUserName = messageErrorUserName;
    }

    public List<UserEntity> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserEntity> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserEntity> getAllUsersEmptySites() {
        return allUsersEmptySites;
    }

    public void setAllUsersEmptySites(List<UserEntity> allUsersEmptySites) {
        this.allUsersEmptySites = allUsersEmptySites;
    }
}
