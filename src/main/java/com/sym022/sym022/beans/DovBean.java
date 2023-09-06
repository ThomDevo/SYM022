package com.sym022.sym022.beans;

import com.sym022.sym022.entities.DovEntity;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.DovService;
import com.sym022.sym022.services.EventService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class DovBean extends FilterOfTable<DovEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private DovEntity dov = new DovEntity();
    private DovService dovService = new DovService();
    private String messageErrorVisitNd = "hidden";
    private String messageErrorVisitDate = "hidden";
    private String messageErrorVisitNdFalse = "hidden";
    private String messageErrorVisitDateMissing = "hidden";
    private String buttonSuccess = "false";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormDov();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
            initErrorMessageFormDov();
            String dovDate = simpleDateFormat.format(dov.getVisitDate());
            int resultDovDate = dovDate.compareTo(String.valueOf(now));
            if(resultDovDate > 0){
                this.messageErrorVisitDate = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDate = "hidden";
                this.buttonSuccess = "false";
            }
        return redirect;
    }

    /**
     * Method to test the InputVsNd in front end
     * @return messageErrorVisitNd hidden or not and button create/update deactivate or not
     */
    public String testInputVsNd(){
        initErrorMessageFormDov();
        String redirect = "null";
        if(!dov.getVisitYn() && (dov.getVisitNd() == null || Objects.equals(dov.getVisitNd(), ""))){
            this.messageErrorVisitNdFalse = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVisitNdFalse = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to find a Dov based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateDov";
        EntityManager em = EMF.getEM();
        try{
            dov = dovService.findDovByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to reset the form to add or update a DOV
     */
    public void initFormDov(){
        Date now = new Date();
        this.dov.setVisitYn(false);
        this.dov.setVisitNd("");
        this.dov.setVisitDate(null);
        initErrorMessageFormDov();
    }

    /**
     * Method to reset all Error Messages in the form to add or update a DOV
     */
    public void initErrorMessageFormDov(){
        this.messageErrorVisitNd = "hidden";
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitNdFalse = "hidden";
        this.messageErrorVisitDateMissing = "hidden";
        this.buttonSuccess = "false";
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
     * Method to create a Dov and a AuditTrail in the DB
     * @return a DOV and an auditTrail
     */
    public String submitFormAddDov(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        DovService dovService = new DovService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(dov.getVisitDate() != null){
            initErrorMessageFormDov();
            String dovDate = simpleDateFormat.format(dov.getVisitDate());
            int resultDovDate = dovDate.compareTo(String.valueOf(now));
            if(resultDovDate > 0){
                this.messageErrorVisitDate = "";
                redirect = "null";
            }
            else {
                redirect = getString(em, redirect, transaction, dovService, eventService, auditTrailService);
            }

            return redirect;
        }else{
            redirect = getString(em, redirect, transaction, dovService, eventService, auditTrailService);

            return redirect;}



    }

    /**
     * Method to process submitFormAddDov()
     * @param em
     * @param redirect
     * @param transaction
     * @param dovService
     * @param eventService
     * @param auditTrailService
     * @return a DOV
     */
    private String getString(EntityManager em, String redirect, EntityTransaction transaction, DovService dovService, EventService eventService, AuditTrailService auditTrailService) {
        if(!dov.getVisitYn() && Objects.equals(dov.getVisitNd(), "")){
            initErrorMessageFormDov();
            this.messageErrorVisitNdFalse = "";
            redirect = "null";
            return redirect;
        }else if(dov.getVisitYn() && dov.getVisitDate() == null){
            initErrorMessageFormDov();
            this.messageErrorVisitDateMissing = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                dov.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                if(dov.getVisitYn()){
                    this.dov.setVisitNd("");
                }

                if(!dov.getVisitYn()){
                    this.dov.setVisitDate(null);
                }

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                dovService.addDov(dov,em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addDov method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();

            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addDov = bundle.getString("dov");
            String add = bundle.getString("add");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addDov+" "+add+" "+forThe+" "+addSubject+" "+dov.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            initFormDov();
        }
        return redirect;
    }

    /**
     * Method to update a Dov and a AuditTrail in the DB
     * @return a DOV and an auditTrail
     */
    public String submitFormUpdateDov(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        DovService dovService = new DovService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(dov.getVisitDate() != null){
            initErrorMessageFormDov();
            String dovDate = simpleDateFormat.format(dov.getVisitDate());
            int resultDovDate = dovDate.compareTo(String.valueOf(now));
            if(resultDovDate > 0){
                this.messageErrorVisitDate = "";
                redirect = "null";
            }
            else {
                redirect = getStringUpdate(em, redirect, transaction, dovService, eventService, auditTrailService);
                return redirect;
            }

            return redirect;
        }else{
            if(!dov.getVisitYn() && Objects.equals(dov.getVisitNd(), "")){
                initErrorMessageFormDov();
                this.messageErrorVisitNdFalse = "";
                redirect = "null";
            }else redirect = getStringUpdate(em, redirect, transaction, dovService, eventService, auditTrailService);
            return redirect;
        }



    }

    /**
     * Method to update a Dov and a AuditTrail in the DB
     * @return a DOV and an auditTrail
     */
    private String getStringUpdate(EntityManager em, String redirect, EntityTransaction transaction, DovService dovService, EventService eventService, AuditTrailService auditTrailService) {
        if(dov.getVisitYn() && dov.getVisitDate() == null){
            initErrorMessageFormDov();
            this.messageErrorVisitDateMissing = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                dov.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);
                eventBean.getEvent().setMonitored(false);

                if(dov.getVisitYn()){
                    this.dov.setVisitNd("");
                }

                if(!dov.getVisitYn()){
                    this.dov.setVisitDate(null);
                }

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                dovService.updateDov(dov,em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addDov method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();

            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addDov = bundle.getString("dov");
            String update = bundle.getString("update");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addDov+" "+update+" "+forThe+" "+addSubject+" "+dov.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            initFormDov();
        }
        return redirect;
    }


    /*--- Getters and Setters ---*/

    public DovEntity getDov() {
        return dov;
    }

    public void setDov(DovEntity dov) {
        this.dov = dov;
    }

    public DovService getDovService() {
        return dovService;
    }

    public void setDovService(DovService dovService) {
        this.dovService = dovService;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public AuditTrailBean getAuditTrailBean() {
        return auditTrailBean;
    }

    public void setAuditTrailBean(AuditTrailBean auditTrailBean) {
        this.auditTrailBean = auditTrailBean;
    }

    public String getMessageErrorVisitNd() {
        return messageErrorVisitNd;
    }

    public void setMessageErrorVisitNd(String messageErrorVisitNd) {
        this.messageErrorVisitNd = messageErrorVisitNd;
    }

    public String getMessageErrorVisitDate() {
        return messageErrorVisitDate;
    }

    public void setMessageErrorVisitDate(String messageErrorVisitDate) {
        this.messageErrorVisitDate = messageErrorVisitDate;
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public String getMessageErrorVisitNdFalse() {
        return messageErrorVisitNdFalse;
    }

    public void setMessageErrorVisitNdFalse(String messageErrorVisitNdFalse) {
        this.messageErrorVisitNdFalse = messageErrorVisitNdFalse;
    }

    public String getMessageErrorVisitDateMissing() {
        return messageErrorVisitDateMissing;
    }

    public void setMessageErrorVisitDateMissing(String messageErrorVisitDateMissing) {
        this.messageErrorVisitDateMissing = messageErrorVisitDateMissing;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }
}
