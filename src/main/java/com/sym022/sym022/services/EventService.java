package com.sym022.sym022.services;

import com.sym022.sym022.entities.EventEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EventService {

    /**
     * Method to find all events
     * @param em
     * @return List of events
     */
    public List<EventEntity> findEventAll (EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventAll", EventEntity.class)
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
}
