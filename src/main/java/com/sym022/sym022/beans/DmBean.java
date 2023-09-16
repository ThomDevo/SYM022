package com.sym022.sym022.beans;

import com.sym022.sym022.entities.DmEntity;
import com.sym022.sym022.enums.Aeout;
import com.sym022.sym022.enums.Culture;
import com.sym022.sym022.enums.Ethnicity;
import com.sym022.sym022.enums.Sex;
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
    private String messageErrorYearOfBirth = "hidden";
    private String messageErrorDmSex = "hidden";
    private String messageErrorDmEthnicity = "hidden";
    private String messageErrorDmCulture = "hidden";
    private String buttonSuccess = "false";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*--- Method ---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormDm();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateForm(){
        String redirect = "/VIEW/home";
        initFormDm();
        return redirect;
    }

    /**
     * Method to reset the form to add or update an IC
     */
    public void initFormDm(){
        this.dm.setYearOfBirth(1920);
        this.dm.setSex(null);
        this.dm.setEthnicity(null);
        this.dm.setCulture(null);
        this.messageErrorYearOfBirth = "hidden";
        this.messageErrorDmSex = "hidden";
        this.messageErrorDmEthnicity = "hidden";
        this.messageErrorDmCulture = "hidden";
        this.setButtonSuccess("false");
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
     * Method to test the year of birth in front end
     * @return messageErrorYearOfBirth hidden or not and button create/update deactivate or not
     */
    public String testDmYearOfBirth(){
        String redirect = "null";
        if(dm.getYearOfBirth() < 1920 || dm.getYearOfBirth() > 2005){
            this.messageErrorYearOfBirth = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorYearOfBirth = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the AllEnumsDm in front end
     * @return messageErrorDmSex, messageErrorDmEthnicity, messageErrorDmCulture hidden or not and button create/update deactivate or not
     */
    public String testAllEnumsDm(){
        String redirect = "null";
        if(dm.getSex() == null){
            this.messageErrorDmSex = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorDmSex = "hidden";
            this.buttonSuccess = "false";
        }

        if(dm.getEthnicity() == null){
            this.messageErrorDmEthnicity = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorDmEthnicity = "hidden";
            this.buttonSuccess = "false";
        }

        if(dm.getCulture() == null){
            this.messageErrorDmCulture = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorDmCulture = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to find a DM based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateDm";
        EntityManager em = EMF.getEM();
        try{
            dm = dmService.findDmByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to find a DM based on the IdEvent
     * @param idEvent
     */
    public String findEventQuery(int idEvent){
        String redirect = "/VIEW/consultQueryDm";
        EntityManager em = EMF.getEM();
        try{
            dm = dmService.findDmByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
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

        if(dm.getYearOfBirth() < 1920 || dm.getYearOfBirth() > 2005){
            this.messageErrorYearOfBirth = "";
            redirect = null;
            return redirect;
        }else if(dm.getSex() == null){
            this.messageErrorDmSex = "";
            redirect = null;
            return redirect;
        }else if(dm.getEthnicity() == null){
            this.messageErrorDmEthnicity = "";
            redirect = null;
            return redirect;
        }else if(dm.getCulture() == null){
            this.messageErrorDmCulture = "";
            redirect = null;
            return redirect;
        }else{
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
        }
        return redirect;
    }

    /**
     * Method to add a DM in the DB
     * @return a DM
     */
    public String submitFormUpdateDm(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        DmService dmService = new DmService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();

        if(dm.getYearOfBirth() < 1920 || dm.getYearOfBirth() > 2005){
            this.messageErrorYearOfBirth = "";
            redirect = null;
            return redirect;
        }else if(dm.getSex() == null){
            this.messageErrorDmSex = "";
            redirect = null;
            return redirect;
        }else if(dm.getEthnicity() == null){
            this.messageErrorDmEthnicity = "";
            redirect = null;
            return redirect;
        }else if(dm.getCulture() == null){
            this.messageErrorDmCulture = "";
            redirect = null;
            return redirect;
        }else{
            try{
                dm.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);
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
            String update = bundle.getString("update");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addDm+" "+update+" "+forThe+" "+addSubject+" "+dm.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            initFormDm();
        }

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

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrorYearOfBirth() {
        return messageErrorYearOfBirth;
    }

    public void setMessageErrorYearOfBirth(String messageErrorYearOfBirth) {
        this.messageErrorYearOfBirth = messageErrorYearOfBirth;
    }

    public String getMessageErrorDmSex() {
        return messageErrorDmSex;
    }

    public void setMessageErrorDmSex(String messageErrorDmSex) {
        this.messageErrorDmSex = messageErrorDmSex;
    }

    public String getMessageErrorDmEthnicity() {
        return messageErrorDmEthnicity;
    }

    public void setMessageErrorDmEthnicity(String messageErrorDmEthnicity) {
        this.messageErrorDmEthnicity = messageErrorDmEthnicity;
    }

    public String getMessageErrorDmCulture() {
        return messageErrorDmCulture;
    }

    public void setMessageErrorDmCulture(String messageErrorDmCulture) {
        this.messageErrorDmCulture = messageErrorDmCulture;
    }
}
