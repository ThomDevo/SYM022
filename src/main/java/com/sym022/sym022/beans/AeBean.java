package com.sym022.sym022.beans;

import com.sym022.sym022.entities.AeEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.services.AeService;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.VisitService;
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
public class AeBean extends FilterOfTable<AeEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private AeEntity ae = new AeEntity();
    private AeService aeService = new AeService();
    private String messageErrorVisitDate = "hidden";
    private String messageErrorVisitNdFalse = "hidden";
    private String messageErrorAeendatBeforAestdat = "hidden";
    private String messageErrorAeendatPres = "hidden";
    private String messageErrorAeendatMis = "hidden";
    private String messageErrorAeotherspMis = "hidden";
    private String messageErrorAemedimspMis = "hidden";
    private String messageErrorVisitDateAeendat = "hidden";
    private String messageErrorAeterm = "hidden";
    private String messageErrorAaeser = "hidden";
    private String messageErrormessageErrorAeendatMis = "hidden";
    private String buttonSuccess = "false";
    LocalDate now = LocalDate.now();
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
        initFormAe();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to reset the form to add or update an AE
     */
    public void initFormAe(){
        Date now = new Date();
        this.ae.setAeterm("");
        this.ae.setAetermc("");
        this.ae.setAestdat(now);
        this.ae.setAeout(Aeout.UNKNOWN);
        this.ae.setAeendat(now);
        this.ae.setAetoxgd(Aetoxgd.NA);
        this.ae.setAesev(Aesev.NA);
        this.ae.setAerel(Aerel.NA);
        this.ae.setAeacn(Aeacn.NOT_APPLICABLE);
        this.ae.setAecm(false);
        this.ae.setAeproc(false);
        this.ae.setAeother(false);
        this.ae.setAeothersp("");
        this.ae.setAeser(false);
        this.ae.setAedeath(false);
        this.ae.setAelife(false);
        this.ae.setAehosp(false);
        this.ae.setAedisab(false);
        this.ae.setAecong(false);
        this.ae.setAemedim(false);
        this.ae.setAemedimsp("");
        initErrorMessageFormAe();
    }

    /**
     * Method to reset all Error Messages in the form to add or update an AE
     */
    public void initErrorMessageFormAe(){
        this.messageErrorAeterm = "hidden";
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitDateAeendat = "hidden";
        this.messageErrorAeendatBeforAestdat = "hidden";
        this.messageErrorAeotherspMis = "hidden";
        this.messageErrorAaeser = "hidden";
        this.messageErrorAemedimspMis = "hidden";
        this.messageErrormessageErrorAeendatMis = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDateNull(){
        String redirect = "null";
        if(ae.getAestdat() == null){
            this.messageErrorVisitNdFalse = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVisitNdFalse = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the aeendat in front end
     * @return messageErrorAeendatMis hidden or not and button create/update deactivate or not
     */
    public String testDateaeendatNull(){
        String redirect = "null";
        if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            this.messageErrorAeendatMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeendatMis = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(ae.getAestdat() == null){
            testDateNull();
        }else{
            String aeDate = simpleDateFormat.format(ae.getAestdat());
            int resultAeDate = aeDate.compareTo(String.valueOf(now));
            if(resultAeDate > 0){
                this.messageErrorVisitDate = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDate = "hidden";
                this.buttonSuccess = "false";
            }
        }
        return redirect;
    }

    /**
     * Method to test the aeterm empty in front end
     * @return messageErrorAeterm hidden or not and button create/update deactivate or not
     */
    public String testAeterm(){
        String redirect = "null";
        if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
            this.messageErrorAeterm = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeterm = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDatetestDateAeout(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            testDateaeendatNull();
        }else{
            String aeoutDate = simpleDateFormat.format(ae.getAeendat());
            int resultAeoutDate = aeoutDate.compareTo(String.valueOf(now));
            if(resultAeoutDate > 0){
                this.messageErrorVisitDateAeendat = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDateAeendat = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test the AeendatBeforAestdat in front end
     * @return messageErrorAeendatBeforAestdat hidden or not and button create/update deactivate or not
     */
    public String testAeendatBeforAestdat(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        if(ae.getAestdat() == null){
            testDateNull();
        }else if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            testDateaeendatNull();
        }else{
            String aeendatDate = simpleDateFormat.format(ae.getAeendat());
            String aeestdatDate = simpleDateFormat.format(ae.getAestdat());
            int resultAendatDate = aeendatDate.compareTo(aeestdatDate);
            if(resultAendatDate < 0){
                this.messageErrorAeendatBeforAestdat = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorAeendatBeforAestdat = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test the AemedimspMis empty in front end
     * @return messageErrorAemedimspMis hidden or not and button create/update deactivate or not
     */
    public String testAemedimspMis(){
        String redirect = "null";
        if(ae.isAemedim() && (ae.getAemedimsp() == null || Objects.equals(ae.getAemedimsp(), ""))){
            this.messageErrorAemedimspMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAemedimspMis = "hidden";
            this.buttonSuccess = "false";
        }

        return redirect;
    }

    /**
     * Method to test the AeotherspMis empty in front end
     * @return messageErrorAeotherspMis hidden or not and button create/update deactivate or not
     */
    public String testAeotherspMis(){
        String redirect = "null";
        if(ae.isAeother() && (ae.getAeothersp() == null || Objects.equals(ae.getAeothersp(), ""))){
            this.messageErrorAeotherspMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeotherspMis = "hidden";
            this.buttonSuccess = "false";
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
     * Method to find a TE based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateDAe";
        EntityManager em = EMF.getEM();
        try{
            ae = aeService.findAeByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to add an AE in the DB
     * @return an AE
     */
    public String submitFormAddAe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        AeService aeService = new AeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";


        if(ae.getAestdat() == null && ae.getAeendat() == null){
            initErrorMessageFormAe();
            this.messageErrorVisitNdFalse = "";
            redirect = "null";
        }else if(ae.getAestdat() != null && ae.getAeendat() == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
            String aeStDate = simpleDateFormat.format(ae.getAestdat());
            int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
            if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                initErrorMessageFormAe();
                this.messageErrorAeterm = "";
                redirect = "null";
                return redirect;
            }else if(resultAeDateDate > 0){
                initErrorMessageFormAe();
                this.messageErrorVisitDate = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAeotherspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAemedimspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                initErrorMessageFormAe();
                this.messageErrorAaeser = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    ae.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    ae.setAetermc("");

                    if(!ae.isAeother()){
                        this.ae.setAeothersp("");
                    }

                    if(!ae.isAemedim()){
                        this.ae.setAemedimsp("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    aeService.addAe(ae, em);
                    transaction.commit();
                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                        FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String addAe = bundle.getString("ae");
                String add = bundle.getString("add");
                String forThe = bundle.getString("for");
                String addSubject = bundle.getString("subject");

                addMessage(addAe+" "+add+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                initFormAe();
            }
        }else{
            if(ae.getAestdat() != null && ae.getAeendat() != null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
                String aeStDate = simpleDateFormat.format(ae.getAestdat());
                int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
                String aeendateDate = simpleDateFormat.format(ae.getAeendat());
                int resultAendateDate = aeendateDate.compareTo(aeStDate);
                String aeoutDate = simpleDateFormat.format(ae.getAeendat());
                int resultAeoutDate = aeoutDate.compareTo(String.valueOf(now));
                if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                    initErrorMessageFormAe();
                    this.messageErrorAeterm = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeDateDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDate = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeoutDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDateAeendat = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAendateDate < 0){
                    initErrorMessageFormAe();
                    this.messageErrorAeendatBeforAestdat = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAeotherspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAemedimspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                    initErrorMessageFormAe();
                    this.messageErrorAaeser = "";
                    redirect = "null";
                    return redirect;
                }else{
                    try{
                        ae.setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                        auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                        eventBean.getEvent().setCompleted(true);
                        ae.setAetermc("");

                        if(!ae.isAeother()){
                            this.ae.setAeothersp("");
                        }

                        if(!ae.isAemedim()){
                            this.ae.setAemedimsp("");
                        }

                        transaction.begin();
                        eventService.updateEvent(eventBean.getEvent(),em);
                        auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                        aeService.addAe(ae, em);
                        transaction.commit();
                    }catch(Exception e){
                        ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                    }finally {
                        if(transaction.isActive()){
                            transaction.rollback();
                        }
                        em.close();
                    }
                    ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
                    String addAe = bundle.getString("ae");
                    String add = bundle.getString("add");
                    String forThe = bundle.getString("for");
                    String addSubject = bundle.getString("subject");

                    addMessage(addAe+" "+add+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                    initFormAe();
                }
            }
        }
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public AeEntity getAe() {
        return ae;
    }

    public void setAe(AeEntity ae) {
        this.ae = ae;
    }

    public AeService getAeService() {
        return aeService;
    }

    public void setAeService(AeService aeService) {
        this.aeService = aeService;
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

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public String getMessageErrorVisitDate() {
        return messageErrorVisitDate;
    }

    public void setMessageErrorVisitDate(String messageErrorVisitDate) {
        this.messageErrorVisitDate = messageErrorVisitDate;
    }

    public String getMessageErrorVisitNdFalse() {
        return messageErrorVisitNdFalse;
    }

    public void setMessageErrorVisitNdFalse(String messageErrorVisitNdFalse) {
        this.messageErrorVisitNdFalse = messageErrorVisitNdFalse;
    }

    public String getMessageErrorAeendatBeforAestdat() {
        return messageErrorAeendatBeforAestdat;
    }

    public void setMessageErrorAeendatBeforAestdat(String messageErrorAeendatBeforAestdat) {
        this.messageErrorAeendatBeforAestdat = messageErrorAeendatBeforAestdat;
    }

    public String getMessageErrorAeendatPres() {
        return messageErrorAeendatPres;
    }

    public void setMessageErrorAeendatPres(String messageErrorAeendatPres) {
        this.messageErrorAeendatPres = messageErrorAeendatPres;
    }

    public String getMessageErrorAeendatMis() {
        return messageErrorAeendatMis;
    }

    public void setMessageErrorAeendatMis(String messageErrorAeendatMis) {
        this.messageErrorAeendatMis = messageErrorAeendatMis;
    }

    public String getMessageErrorAeotherspMis() {
        return messageErrorAeotherspMis;
    }

    public void setMessageErrorAeotherspMis(String messageErrorAeotherspMis) {
        this.messageErrorAeotherspMis = messageErrorAeotherspMis;
    }

    public String getMessageErrorAemedimspMis() {
        return messageErrorAemedimspMis;
    }

    public void setMessageErrorAemedimspMis(String messageErrorAemedimspMis) {
        this.messageErrorAemedimspMis = messageErrorAemedimspMis;
    }

    public String getMessageErrorVisitDateAeendat() {
        return messageErrorVisitDateAeendat;
    }

    public void setMessageErrorVisitDateAeendat(String messageErrorVisitDateAeendat) {
        this.messageErrorVisitDateAeendat = messageErrorVisitDateAeendat;
    }

    public String getMessageErrorAeterm() {
        return messageErrorAeterm;
    }

    public void setMessageErrorAeterm(String messageErrorAeterm) {
        this.messageErrorAeterm = messageErrorAeterm;
    }

    public String getMessageErrorAaeser() {
        return messageErrorAaeser;
    }

    public void setMessageErrorAaeser(String messageErrorAaeser) {
        this.messageErrorAaeser = messageErrorAaeser;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrormessageErrorAeendatMis() {
        return messageErrormessageErrorAeendatMis;
    }

    public void setMessageErrormessageErrorAeendatMis(String messageErrormessageErrorAeendatMis) {
        this.messageErrormessageErrorAeendatMis = messageErrormessageErrorAeendatMis;
    }
}
