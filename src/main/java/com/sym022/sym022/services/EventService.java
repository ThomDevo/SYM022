package com.sym022.sym022.services;

import com.sym022.sym022.entities.EventEntity;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EventService {

    /**
     * Method to find all events
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAll (int idUser, String researchWord,EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventAll", EventEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all events who are not monitored
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAllNotMonitored (int idUser,String researchWord,EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventAllNotMonitored", EventEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all events who are not monitored
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAllNotCodedAe(int idUser,String researchWord,EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventAllNotCodedAe", EventEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all events Medical
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAllMedicalPermitted(int idUser,String researchWord,EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventMedicalPermitted", EventEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all events who are not monitored
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAllNotCodedCm(int idUser,String researchWord,EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventAllNotCodedCm", EventEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find an event by id
      * @param idEvent
     * @param em
     * @return event
     */
    public EventEntity findEventById(int idEvent, EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventById", EventEntity.class)
                .setParameter("idEvent", idEvent)
                .getSingleResult();
    }

    /**
     * Method to find all events with query
     * @param em
     * @return List of event
     */
    public List<EventEntity> findEventWithQuery (EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventWithQuery", EventEntity.class)
                .getResultList();
    }

    /**
     * Method to find the number of occurrence of events by idSubject and idForm
     * @param idSubject
     * @param idForm
     * @param em
     * @return number of events
     */
    public List<EventEntity> findNumberOfEvents (int idSubject, int idForm, EntityManager em){
        return em.createNamedQuery("Event.selectCountEventOccurrence", EventEntity.class)
                .setParameter("idSubject", idSubject)
                .setParameter("idForm", idForm)
                .getResultList();
    }

    /**
     * * Method to check if subject has already an event
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean isEventSubjectExist(int idSubject, EntityManager em){
        Query query =em.createNamedQuery("Event.CheckNewPatient", EventEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getSingleResult()).intValue();
        //ProcessUtils.debug(""+count);
        return count > 0;
    }

    /**
     * * Method to check if subject has already an Ic form
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean isIcSubjectExist(int idSubject, EntityManager em){
        Query query =em.createNamedQuery("Event.CheckIcCompleted", EventEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * * Method to check if subject has already started Mois1
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean isMois1SubjectExist(int idSubject, EntityManager em){
        Query query =em.createNamedQuery("Event.CheckMois1Started", EventEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find all events except Ae and Cm
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAllExceptAeCm (int idSubject, EntityManager em)
    {
        return em.createNamedQuery("Event.CheckSubjectCompleted", EventEntity.class)
                .setParameter("idSubject", idSubject)
                .getResultList();
    }

    /**
     * Method to find all permitted events for the User
     * @param idUser
     * @param em
     * @return List of events
     */
    public List<EventEntity> findPermittedEventAll (int idUser, EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventPermitted", EventEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to find all events from Screening
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean findEventScreening (int idSubject, EntityManager em)
    {
        Query query =em.createNamedQuery("Event.selectEventScreening", EventEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getResultList().size()).intValue();
        return count < 4;
    }

    /**
     * Method to find all events from Dov
     * @param idSubject
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventDOV (int idSubject, EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventDov", EventEntity.class)
                .setParameter("idSubject", idSubject)
                .getResultList();
    }

    /**
     * Method to find all events from Mois_1
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean findEventMois1 (int idSubject, EntityManager em)
    {
        Query query =em.createNamedQuery("Event.selectEventMois1", EventEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getResultList().size()).intValue();
        return count < 3;
    }

    /**
     * Method to add a new event
     * @param event
     * @param em
     * @return event
     */
    public EventEntity addEvent(EventEntity event, EntityManager em){
        em.persist(event);
        em.flush();
        return event;
    }

    /**
     * Method to update an event
     * @param event
     * @param em
     * @return event
     */
    public EventEntity updateEvent(EventEntity event, EntityManager em){
        em.merge(event);
        return event;
    }

    /**
     * Method to delete an event
     * @param event
     * @param em
     */
    public void deleteEvent(EventEntity event, EntityManager em){
        if(!em.contains(event))
            event = em.merge(event);
        em.remove(event);
        em.flush();
    }
}
