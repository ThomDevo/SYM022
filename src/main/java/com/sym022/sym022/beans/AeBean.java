package com.sym022.sym022.beans;

import com.sym022.sym022.entities.AeEntity;
import com.sym022.sym022.services.AeService;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String aeDate = simpleDateFormat.format(ae.getAeendat());
        int resultAeDate = aeDate.compareTo(String.valueOf(now));
        if(resultAeDate > 0){
            this.messageErrorVisitDate = "";
        }else{
            this.messageErrorVisitDate = "hidden";
        }
        return redirect;
    }

    public String submitFormAddAe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        AeService aeService = new AeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
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
}
