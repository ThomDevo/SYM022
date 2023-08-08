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
     * Method to fin all events with query
     * @param em
     * @return List of event
     */
    public List<EventEntity> findEventWithQuery (EntityManager em)
    {
        return em.createNamedQuery("Event.selectEventWithQuery", EventEntity.class)
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
}
