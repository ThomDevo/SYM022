package com.sym022.sym022.beans;

import com.sym022.sym022.entities.CmEntity;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.CmService;
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
public class CmBean extends FilterOfTable <CmEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private CmEntity cm = new CmEntity();
    private CmService cmService = new CmService();
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/
    /**
     * Method to reset the form to add or update an IC
     */
    public void initFormCm(){

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
     * Method to add a CM in the DB
     * @return a CM
     */
    public String submitFormAddCm(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        CmService cmService = new CmService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        try{
            cm.setEventByIdEvent(eventBean.getEvent());
            auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
            auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
            auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
            eventBean.getEvent().setCompleted(true);

            transaction.begin();
            eventService.updateEvent(eventBean.getEvent(),em);
            auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
            cmService.addCm(cm, em);
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
        String addCm = bundle.getString("cm");
        String add = bundle.getString("add");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addCm+" "+add+" "+forThe+" "+addSubject+" "+cm.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFormCm();
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public CmEntity getCm() {
        return cm;
    }

    public void setCm(CmEntity cm) {
        this.cm = cm;
    }

    public CmService getCmService() {
        return cmService;
    }

    public void setCmService(CmService cmService) {
        this.cmService = cmService;
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
