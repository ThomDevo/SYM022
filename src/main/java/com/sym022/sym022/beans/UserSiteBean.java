package com.sym022.sym022.beans;

import com.sym022.sym022.entities.UserEntity;
import com.sym022.sym022.entities.UserSiteEntity;
import com.sym022.sym022.services.UserSiteService;
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
public class UserSiteBean extends FilterOfTable<UserSiteEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private UserSiteService userSiteService = new UserSiteService();
    private UserSiteEntity userSite = new UserSiteEntity();
    private List<UserSiteEntity> allUserSites;
    private List<UserSiteEntity> allUserSitesPerUser;
    @Inject
    private UserBean userBean;
    @Inject
    private SiteBean siteBean;

    /*---Method---*/

    /**
     * Method to initialize the list of sites per user
     * @param user
     */
    public void initListOfSitesPerUser(UserEntity user){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        UserSiteService userSiteService = new UserSiteService();
        try{
            allUserSitesPerUser = userSiteService.findUserSiteByIdUser(user.getIdUser(),em);
            siteBean.setAllSiteSelected(allUserSitesPerUser.stream().map(UserSiteEntity::getSiteByIdSite).collect(Collectors.toList()));
        }catch(Exception e){
            allUserSitesPerUser = new ArrayList<>();
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to have all the userSite
     */
    public void allUserSites(){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        UserSiteService userSiteService = new UserSiteService();
        try{
            transaction.begin();
            filterOfTable = userSiteService.findAll(this.filter,em);
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
     * Method to add a List of userSites for a user in DB
     * @return a userSite
     */
    public String submitFormAddUserSites(){

        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        String redirect = "/VIEW/home";
        UserSiteEntity userSite;
        UserSiteService userSiteService = new UserSiteService();
        try{
            transaction.begin();
            for(int i=0;i< siteBean.getAllSiteSelected().size();i++){
                userSite = new UserSiteEntity();
                userSite.setUserByIdUser(userBean.getUser());
                //ProcessUtils.debug("A "+ userBean.getUser());
                userSite.setSiteByIdSite(siteBean.getAllSiteSelected().get(i));
                userSiteService.addUserSite(userSite,em);
            }
            transaction.commit();
            siteBean.initAllEditorSite();

        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
            return redirect;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String listOfSites = bundle.getString("listOfSites");
        String add = bundle.getString("add");
        addMessage(listOfSites+" "+ add,"Confirmation");
        return redirect;
    }

    /**
     * Method to update a List of userSites for a user in DB
     * @return a userSite
     */
    public String submitFormUpdateUserSite(){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        String redirect = "/VIEW/home";
        UserSiteEntity userSite;
        UserSiteService userSiteService = new UserSiteService();
        boolean findMatch;
        try{
            transaction.begin();
            for(int i=0;i<siteBean.getAllSiteSelected().size();i++){
                findMatch = false;
                for(int j=0;j<allUserSitesPerUser.size();j++){
                    if(siteBean.getAllSiteSelected().get(i).getIdSite() == allUserSitesPerUser.get(j).getSiteByIdSite().getIdSite()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    userSite = new UserSiteEntity();
                    userSite.setUserByIdUser(this.userSite.getUserByIdUser());
                    userSite.setSiteByIdSite(siteBean.getAllSiteSelected().get(i));
                    userSiteService.addUserSite(userSite,em);
                }
            }

            for(int j=0;j<allUserSitesPerUser.size();j++){
                findMatch = false;
                for(int i=0;i<siteBean.getAllSiteSelected().size();i++){
                    if(siteBean.getAllSiteSelected().get(i).getIdSite() == allUserSitesPerUser.get(j).getSiteByIdSite().getIdSite()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    userSite=userSiteService.findUserSiteByIdUserAndByIdSite(getUserSite().getUserByIdUser().getIdUser(),allUserSitesPerUser.get(j).getSiteByIdSite().getIdSite(),em);
                    userSiteService.deleteUserSite(userSite,em);
                }
            }
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
            return redirect;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String listOfSites = bundle.getString("listOfSites");
        String update = bundle.getString("update");
        addMessage(listOfSites+" "+ update,"Confirmation");
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

    public UserSiteService getUserSiteService() {
        return userSiteService;
    }

    public void setUserSiteService(UserSiteService userSiteService) {
        this.userSiteService = userSiteService;
    }

    public UserSiteEntity getUserSite() {
        return userSite;
    }

    public void setUserSite(UserSiteEntity userSite) {
        this.userSite = userSite;
    }

    public List<UserSiteEntity> getAllUserSites() {
        return allUserSites;
    }

    public void setAllUserSites(List<UserSiteEntity> allUserSites) {
        this.allUserSites = allUserSites;
    }

    public List<UserSiteEntity> getAllUserSitesPerUser() {
        return allUserSitesPerUser;
    }

    public void setAllUserSitesPerUser(List<UserSiteEntity> allUserSitesPerUser) {
        this.allUserSitesPerUser = allUserSitesPerUser;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public SiteBean getSiteBean() {
        return siteBean;
    }

    public void setSiteBean(SiteBean siteBean) {
        this.siteBean = siteBean;
    }
}
