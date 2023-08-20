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
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    /**
     * Method to reset the form to add or update a DOV
     */
    public void initFormDov(){
        Date now = new Date();
        this.dov.setVisitYn(true);
        this.dov.setVisitNd("");
        this.dov.setVisitDate(now);
        initErrorMessageFormDov();
    }

    /**
     * Method to reset all Error Messages in the form to add or update a DOV
     */
    public void initErrorMessageFormDov(){
        this.messageErrorVisitNd = "hidden";
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitNdFalse = "hidden";
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

    public String submitFormAddDov(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        DovService dovService = new DovService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dovDate = simpleDateFormat.format(dov.getVisitDate());
        int resultDovDate = dovDate.compareTo(String.valueOf(now));
        ProcessUtils.debug(""+ resultDovDate);


        if(!dov.getVisitYn() && Objects.equals(dov.getVisitNd(), "")){
            initErrorMessageFormDov();
            this.messageErrorVisitNdFalse = "";
            redirect = "null";
        }else if(dov.getVisitYn() && !Objects.equals(dov.getVisitNd(), "")){
            initErrorMessageFormDov();
            this.messageErrorVisitNd = "";
            redirect = "null";
        }else if(resultDovDate > 0){
            initErrorMessageFormDov();
            this.messageErrorVisitDate = "";
            redirect = "null";
        }else{
        try{

                dov.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                dovService.addDov(dov,em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                transaction.commit();



        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addDov method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();

        }
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
}
