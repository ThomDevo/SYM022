package com.sym022.sym022.beans;

import com.sym022.sym022.entities.EventEntity;
import com.sym022.sym022.services.AuditTrailService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class EventBean extends FilterOfTable<EventEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private EventEntity event = new EventEntity();
    private EventService eventService = new EventService();
    private List<EventEntity> allEvents;
    private List<EventEntity> number;
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;

    /*---Method---*/

    /**
     * Method to filter the roles on roleLabel
     */
    public void researchFilterAllEvents(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = eventService.findEventAll(this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void deleteEvent(){
        EntityManager em = EMF.getEM();
        EventService eventService = new EventService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            eventService.deleteEvent(event,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the deleteEvent method: "+ e);
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }

    }

    /**
     * Method to reset the form of ad or update a form
     */
    public void initEvent(){
        this.event.setSubjectByIdSubject(null);
        this.event.setVisitByIdVisit(null);
        this.event.setFormByIdForm(null);
    }


    /**
     * Method to return the number of occurrences of the event
     * @return a String
     */
    public String findNumberOfEvents(){
        EntityManager em = EMF.getEM();
        EventService eventService = new EventService();
        String nextNumber;
        try{
            this.number= eventService.findNumberOfEvents(event.getSubjectByIdSubject().getIdSubject(),event.getFormByIdForm().getIdForm(),em);
        }catch(Exception e){
            this.number = new ArrayList<>();
        }finally{
            em.close();
        }
        nextNumber = String.valueOf(number.size());
        return nextNumber;
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
     * Method to inactivate an event
     * @return an event
     */
    public String inactivateAnEvent() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        try{
            auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
            auditTrailBean.getAuditTrail().setEventByIdEvent(event);
            auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
            event.setAvailable(false);
            transaction.begin();
            eventService.updateEvent(event,em);
            auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
            transaction.commit();
        }catch(Exception e){

            ProcessUtils.debug(" I'm in the catch of the inactiveEvent method: "+ e);
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addEvent = bundle.getString("event");
            String inactive = bundle.getString("inactive");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");
            addMessage(addEvent+" "+inactive+" "+forThe+" "+addSubject+" "+event.getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        }
        return redirect;
    }

    /**
     * Method to inactivate an event
     * @return an event
     */
    public String reactivateAnEvent() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        try{
            auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
            auditTrailBean.getAuditTrail().setEventByIdEvent(event);
            auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
            event.setAvailable(true);
            transaction.begin();
            eventService.updateEvent(event,em);
            auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
            transaction.commit();
        }catch(Exception e){

            ProcessUtils.debug(" I'm in the catch of the inactiveEvent method: "+ e);
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addEvent = bundle.getString("event");
            String reactive = bundle.getString("reactive");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");
            addMessage(addEvent+" "+reactive+" "+forThe+" "+addSubject+" "+event.getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        }
        return redirect;
    }

    /**
     * Method to inactivate an event
     * @return an event
     */
    public String setEventMonitored() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        try{

            auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
            auditTrailBean.getAuditTrail().setEventByIdEvent(event);
            auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
            event.setMonitored(true);
            transaction.begin();
            eventService.updateEvent(event,em);
            auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
            transaction.commit();
        }catch(Exception e){

            ProcessUtils.debug(" I'm in the catch of the inactiveEvent method: "+ e);
        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    /**
     * Method to create an event in the DB
     * @return an event
     */
    public String submitFormAddEvent(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        Date now = new Date();
        try{
            event.setCompleted(false);
            event.setQueried(false);
            event.setMonitored(false);
            event.setAvailable(true);
            event.setOrder(Integer.parseInt(findNumberOfEvents())+1);

            transaction.begin();
            eventService.addEvent(event,em);
            transaction.commit();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addEvent method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        if (event.getFormByIdForm().getFormNum() == 10){
            return "/VIEW/addDov";
        }else if(event.getFormByIdForm().getFormNum() == 20){
            return "/VIEW/addIc";
        }else if(event.getFormByIdForm().getFormNum() == 30){
            return "/VIEW/addDm";
        }else if(event.getFormByIdForm().getFormNum() == 40){
            return "/VIEW/addVs";
        }else if(event.getFormByIdForm().getFormNum() == 50){
            return "/VIEW/addTe";
        }else if(event.getFormByIdForm().getFormNum() == 80){
            return "/VIEW/addAe";
        }else if(event.getFormByIdForm().getFormNum() == 90){
            return "/VIEW/addCm";
        }else{
            return redirect;
        }

    }



    /*--- Getters and Setters ---*/

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public List<EventEntity> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<EventEntity> allEvents) {
        this.allEvents = allEvents;
    }

    public List<EventEntity> getNumber() {
        return number;
    }

    public void setNumber(List<EventEntity> number) {
        this.number = number;
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


}
