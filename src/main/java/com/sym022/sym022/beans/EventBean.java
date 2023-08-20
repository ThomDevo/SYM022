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
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class EventBean extends FilterOfTable<EventEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private EventEntity event = new EventEntity();
    private EventService eventService = new EventService();
    private List<EventEntity> allEvents;
    private List<EventEntity> number;

    /*---Method---*/

    public String deleteEvent(){
        EntityManager em = EMF.getEM();
        EventService eventService = new EventService();
        String redirect = "/VIEW/home";
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
        return redirect;

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
        //ProcessUtils.debug(""+number.size());
        //ProcessUtils.debug(""+nextNumber);
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
        try{
            event.setAvailable(false);
            transaction.begin();
            eventService.updateEvent(event,em);
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
}
