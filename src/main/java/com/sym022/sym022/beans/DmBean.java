package com.sym022.sym022.beans;

import com.sym022.sym022.entities.DmEntity;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.DmService;
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
import java.util.Date;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class DmBean extends FilterOfTable<DmEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private DmEntity dm = new DmEntity();
    private DmService dmService = new DmService();
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*--- Method ---*/

    /**
     * Method to reset the form to add or update an IC
     */
    public void initFormDm(){
        this.dm.setYearOfBirth(1920);
        this.dm.setSex(null);
        this.dm.setEthnicity(null);
        this.dm.setCulture(null);
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
     * Method to add a DM in the DB
     * @return a DM
     */
    public String submitFormAddDm(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        DmService dmService = new DmService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        try{
            dm.setEventByIdEvent(eventBean.getEvent());
            auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
            auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
            auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
            eventBean.getEvent().setCompleted(true);

            transaction.begin();
            eventService.updateEvent(eventBean.getEvent(),em);
            auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
            dmService.addDm(dm, em);
            transaction.commit();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addDM method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addDm = bundle.getString("dm");
        String add = bundle.getString("add");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addDm+" "+add+" "+forThe+" "+addSubject+" "+dm.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFormDm();
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public DmEntity getDm() {
        return dm;
    }

    public void setDm(DmEntity dm) {
        this.dm = dm;
    }

    public DmService getDmService() {
        return dmService;
    }

    public void setDmService(DmService dmService) {
        this.dmService = dmService;
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
}
