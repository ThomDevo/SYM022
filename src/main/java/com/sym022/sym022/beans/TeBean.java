package com.sym022.sym022.beans;

import com.sun.org.apache.xml.internal.serialize.TextSerializer;
import com.sym022.sym022.entities.TeEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.TeService;
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
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class TeBean extends FilterOfTable<TeEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private TeEntity te = new TeEntity();
    private TeService teService = new TeService();
    private String messageErrorVisitNd = "hidden";
    private String messageErrorVisitDate = "hidden";
    private String messageErrorVisitDateMissing = "hidden";
    private String messageErrorCR = "hidden";
    private String messageErrorPR = "hidden";
    private String messageErrorSD = "hidden";
    private String messageErrorPD = "hidden";
    private String messageErrorNAE = "hidden";
    private String messageErrorNE = "hidden";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    /**
     * Method to reset all error messages
     */
    public void initErrorMessageFormTE(){
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitNd = "hidden";
        this.messageErrorCR = "hidden";
        this.messageErrorPR = "hidden";
        this.messageErrorSD = "hidden";
        this.messageErrorPD = "hidden";
        this.messageErrorNAE = "hidden";
        this.messageErrorNE = "hidden";
        this.messageErrorVisitDateMissing = "hidden";
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String teDate = simpleDateFormat.format(te.getTeDate());
        int resultTeDate = teDate.compareTo(String.valueOf(now));
        if(resultTeDate > 0){
            this.messageErrorVisitDate = "";
        }else{
            this.messageErrorVisitDate = "hidden";
        }
        return redirect;
    }

    /**
     * Method to test overallResponse in front end
     * @return all error messages are hidden or not
     */
    public String testOverallResponse(){
        String redirect = "null";
        initErrorMessageFormTE();
        if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
            this.messageErrorCR = "";
        }else{
            this.messageErrorCR = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.NON_COMPLETE_RESPONSE_NON_PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorPR = "";
        }else{
            this.messageErrorPR = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.NOT_EVALUABLE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorPR = "";
        }else{
            this.messageErrorPR = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.NON_COMPLETE_RESPONSE_NON_PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE || te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.NOT_ALL_EVALUATED && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorPR = "";
        }else{
            this.messageErrorPR = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() == NonTargetLesions.NON_COMPLETE_RESPONSE_NON_PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE || te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() == NonTargetLesions.NOT_ALL_EVALUATED && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
            this.messageErrorSD = "";
        }else{
            this.messageErrorSD = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() == NonTargetLesions.NON_COMPLETE_RESPONSE_NON_PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
            this.messageErrorNE = "";
        }else{
            this.messageErrorNE = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE || te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorPD = "";
        }else{
            this.messageErrorPD = "hidden";
        }

        if(te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE && te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorPD = "";
        }else{
            this.messageErrorPD = "hidden";
        }

        if(te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
            this.messageErrorPD = "";
        }else{
            this.messageErrorPD = "hidden";
        }

        if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() == NonTargetLesions.NOT_ALL_EVALUATED && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
            this.messageErrorNAE = "";
        }else{
            this.messageErrorNAE = "hidden";
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
     * Method to add a popup
     */
    private void getRessourceBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addTe = bundle.getString("te");
        String add = bundle.getString("add");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addTe+" "+add+" "+forThe+" "+addSubject+" "+te.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFromTe();
    }

    /**
     * Method to reset the form for Add/Update a VS
     */
    public void initFromTe(){
        Date now = new Date();
        this.te.setTeYn(false);
        this.te.setTeNd("");
        this.te.setTeDate(now);
        this.te.setTargetLesions(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
        this.te.setNonTargetLesions(NonTargetLesions.NOT_ALL_EVALUATED);
        this.te.setNewLesions(false);
        this.te.setOverallResponse(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
        initErrorMessageFormTE();
    }

    /**
     * Method to add a Te in the DB
     * @return a TE
     */
    public String submitFormAddTe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        TeService teService = new TeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(te.getTeYn() && te.getTeDate() != null){
            String teDate = simpleDateFormat.format(te.getTeDate());
            int resultTeDate = teDate.compareTo(String.valueOf(now));
            if(resultTeDate > 0){
                initErrorMessageFormTE();
                this.messageErrorVisitDate = "";
            }else{
                try{
                    te.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    teService.addTe(te,em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addVS method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                getRessourceBundle();
            }
        }else{

        }
        return redirect;
    }


    /*--- Getters and Setters ---*/

    public TeEntity getTe() {
        return te;
    }

    public void setTe(TeEntity te) {
        this.te = te;
    }

    public TeService getTeService() {
        return teService;
    }

    public void setTeService(TeService teService) {
        this.teService = teService;
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

    public String getMessageErrorVisitDateMissing() {
        return messageErrorVisitDateMissing;
    }

    public void setMessageErrorVisitDateMissing(String messageErrorVisitDateMissing) {
        this.messageErrorVisitDateMissing = messageErrorVisitDateMissing;
    }

    public String getMessageErrorCR() {
        return messageErrorCR;
    }

    public void setMessageErrorCR(String messageErrorCR) {
        this.messageErrorCR = messageErrorCR;
    }

    public String getMessageErrorPR() {
        return messageErrorPR;
    }

    public void setMessageErrorPR(String messageErrorPR) {
        this.messageErrorPR = messageErrorPR;
    }

    public String getMessageErrorSD() {
        return messageErrorSD;
    }

    public void setMessageErrorSD(String messageErrorSD) {
        this.messageErrorSD = messageErrorSD;
    }

    public String getMessageErrorPD() {
        return messageErrorPD;
    }

    public void setMessageErrorPD(String messageErrorPD) {
        this.messageErrorPD = messageErrorPD;
    }

    public String getMessageErrorNAE() {
        return messageErrorNAE;
    }

    public void setMessageErrorNAE(String messageErrorNAE) {
        this.messageErrorNAE = messageErrorNAE;
    }

    public String getMessageErrorNE() {
        return messageErrorNE;
    }

    public void setMessageErrorNE(String messageErrorNE) {
        this.messageErrorNE = messageErrorNE;
    }
}
