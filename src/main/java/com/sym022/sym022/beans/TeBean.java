package com.sym022.sym022.beans;

import com.sun.org.apache.xml.internal.serialize.TextSerializer;
import com.sym022.sym022.entities.TeEntity;
import com.sym022.sym022.services.TeService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

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
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    public String submitFormAddTe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
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
}
