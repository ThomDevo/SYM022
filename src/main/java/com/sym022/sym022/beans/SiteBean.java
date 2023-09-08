package com.sym022.sym022.beans;

import com.sym022.sym022.entities.SiteEntity;
import com.sym022.sym022.services.SiteService;
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
import java.util.regex.Pattern;

@Named
@ManagedBean
@SessionScoped
public class SiteBean extends FilterOfTable<SiteEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private SiteEntity site = new SiteEntity();
    private SiteService siteService = new SiteService();
    private String messageErrorSiteNum = "hidden";
    private String messageErrorSiteNumMinMax = "hidden";
    private String messageErrorSiteName = "hidden";
    private String messageErrorPiName = "hidden";
    private String buttonSuccess = "false";
    private List<SiteEntity> allSite;
    private List<SiteEntity> allSiteSelected;
    private List<SiteEntity> allSitePermitted;
    @Inject
    private ConnectionBean connectionBean;


    /*--- Methods ---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormSite();
        return redirect;
    }


    /**
     * Method to reset the form to add or update a site
     */
    public void initFormSite(){
        this.site.setSiteNum(1000);
        this.site.setSiteName("");
        this.site.setPiName("");
        initErrorMessageFormCm();
    }

    /**
     * Method to reset all Error Messages in the form to add or update a Site
     */
    public void initErrorMessageFormCm(){
        this.messageErrorSiteNum = "hidden";
        this.messageErrorSiteNumMinMax = "hidden";
        this.messageErrorSiteName = "hidden";
        this.messageErrorPiName = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the SiteName empty in front end
     * @return messageErrorSiteName hidden or not and button create/update deactivate or not
     */
    public String testSiteNum(){
        String redirect = "null";
        if(site.getSiteNum() < 1000 || site.getSiteNum() > 9999){
            this.messageErrorSiteNumMinMax = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorSiteNumMinMax = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the SiteName empty in front end
     * @return messageErrorSiteName hidden or not and button create/update deactivate or not
     */
    public String testSiteName(){
        String redirect = "null";
        if(!Pattern.matches("^\\D{2,200}$", site.getSiteName())){
            this.messageErrorSiteName = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorSiteName = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the PiName empty in front end
     * @return messageErrorPiName hidden or not and button create/update deactivate or not
     */
    public String testPiName(){
        String redirect = "null";

        if(!Pattern.matches("^\\D{2,200}$", site.getPiName())){
            this.messageErrorPiName = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorPiName = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to have all sites in the select menu
     */
    public void initAllEditorSite(){
        EntityManager em = EMF.getEM();
        SiteService siteService = new SiteService();
        EntityTransaction transaction = em.getTransaction();
         try{
            transaction.begin();
            this.allSite = siteService.findSiteAll(em);
            transaction.commit();
        }catch(Exception e){
            this.allSite = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to have all permitted sites in the select menu
     */
    public void initAllEditorPermittedSite(){
        EntityManager em = EMF.getEM();
        SiteService siteService = new SiteService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allSitePermitted = siteService.findSiteByUserConnected(connectionBean.getUser().getIdUser(),em);
            transaction.commit();
        }catch(Exception e){
            this.allSitePermitted = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to filter the sites on the siteNum, the siteName and the piName
     */
    public void researchFilterSite(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = siteService.findSiteByFilter(this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }
    }

    /**
     * Method to add a new site in the DB
     * @return Site
     */
    public String submitFormAddSite(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        SiteService siteService = new SiteService();

        if(site.getSiteNum()< 1000 || site.getSiteNum() > 9999){
            initErrorMessageFormCm();
            this.messageErrorSiteNum = "";
            redirect = null;
            return redirect;
        }else if(!Pattern.matches("^\\D{2,200}$", site.getSiteName())){
            initErrorMessageFormCm();
            this.messageErrorSiteName = "";
            redirect = null;
        }else if(!Pattern.matches("^\\D{2,200}$", site.getPiName())){
            initErrorMessageFormCm();
            this.messageErrorPiName = "";
            redirect = null;
        }else{
            try{
                setSiteNameInCapitalization(site.getSiteName());
                site.setSiteStatus(true);
                transaction.begin();
                if(siteService.isSiteExist(site.getSiteNum(),em)){
                    this.messageErrorSiteNum="";
                    redirect = "null" ;
                    return redirect;
                }
                siteService.addSite(site,em);
                transaction.commit();
                confirmAddSite();
                initFormSite();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addSite method: "+ e);
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
     * Method to update a site in the DB
     * @return
     */
    public String submitFormUpdateSite(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        SiteService siteService = new SiteService();

        if(!Pattern.matches("^\\D{2,200}$", site.getSiteName())){
            initErrorMessageFormCm();
            this.messageErrorSiteName = "";
            redirect = null;
        }else if(!Pattern.matches("^\\D{2,200}$", site.getPiName())){
            initErrorMessageFormCm();
            this.messageErrorPiName = "";
            redirect = null;
        }else{
            try{
                setSiteNameInCapitalization(site.getSiteName());
                transaction.begin();
                siteService.updateSite(site,em);
                transaction.commit();
                confirmUpdateSite();
                initFormSite();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the updateSite method: "+ e);
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
     * Method to have I18n messages in Back-end
     * @param summary
     * @param detail
     */
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Method for getting a popup confirming that the site has been added
     */
    public void confirmAddSite(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addSite = bundle.getString("site");
        String add = bundle.getString("add");

        addMessage(addSite+" "+site.getSiteName()+" "+add,"Confirmation");
    }

    /**
     * Method for getting a popup confirming that the site has been updated
     */
    public void confirmUpdateSite(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addSite = bundle.getString("site");
        String update = bundle.getString("update");

        addMessage(addSite+" "+site.getSiteName()+" "+update,"Confirmation");
    }

    /**
     * Method to set the site name with the first letter in Uppercase
     * @param siteName
     */
    public void setSiteNameInCapitalization(String siteName){
        site.setSiteName(site.getSiteName().substring(0,1).toUpperCase()+site.getSiteName().substring(1).toLowerCase());
    }

    /*--- Getters and Setters ---*/

    public SiteEntity getSite() {
        return site;
    }

    public void setSite(SiteEntity site) {
        this.site = site;
    }

    public SiteService getSiteService() {
        return siteService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    public String getMessageErrorSiteNum() {
        return messageErrorSiteNum;
    }

    public void setMessageErrorSiteNum(String messageErrorSiteNum) {
        this.messageErrorSiteNum = messageErrorSiteNum;
    }

    public List<SiteEntity> getAllSite() {
        return allSite;
    }

    public void setAllSite(List<SiteEntity> allSite) {
        this.allSite = allSite;
    }

    public List<SiteEntity> getAllSiteSelected() {
        return allSiteSelected;
    }

    public void setAllSiteSelected(List<SiteEntity> allSiteSelected) {
        this.allSiteSelected = allSiteSelected;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public List<SiteEntity> getAllSitePermitted() {
        return allSitePermitted;
    }

    public void setAllSitePermitted(List<SiteEntity> allSitePermitted) {
        this.allSitePermitted = allSitePermitted;
    }

    public String getMessageErrorSiteName() {
        return messageErrorSiteName;
    }

    public void setMessageErrorSiteName(String messageErrorSiteName) {
        this.messageErrorSiteName = messageErrorSiteName;
    }

    public String getMessageErrorPiName() {
        return messageErrorPiName;
    }

    public void setMessageErrorPiName(String messageErrorPiName) {
        this.messageErrorPiName = messageErrorPiName;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrorSiteNumMinMax() {
        return messageErrorSiteNumMinMax;
    }

    public void setMessageErrorSiteNumMinMax(String messageErrorSiteNumMinMax) {
        this.messageErrorSiteNumMinMax = messageErrorSiteNumMinMax;
    }
}
