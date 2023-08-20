package com.sym022.sym022.services;

import com.sym022.sym022.entities.EventEntity;
import javax.persistence.EntityManager;
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
