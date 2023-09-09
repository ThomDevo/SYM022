package com.sym022.sym022.services;

import com.sym022.sym022.entities.TeEntity;

import javax.persistence.EntityManager;

public class TeService {

    /**
     * Method to find a TE based on the id
     * @param idTe
     * @param em
     * @return a TE
     */
    public TeEntity findTeById(int idTe, EntityManager em){
        return em.createNamedQuery("Te.selectTeById", TeEntity.class)
                .setParameter("idTe",idTe)
                .getSingleResult();
    }

    /**
     * Method to find a TE based on the id
     * @param idEvent
     * @param em
     * @return a TE
     */
    public TeEntity findTeByIdEvent(int idEvent, EntityManager em){
        return em.createNamedQuery("Te.selectTeByIdEvent", TeEntity.class)
                .setParameter("idEvent",idEvent)
                .getSingleResult();
    }

    /**
     * Method to add a Te in the DB
     * @param te
     * @param em
     * @return a TE
     */
    public TeEntity addTe(TeEntity te, EntityManager em){
        em.persist(te);
        em.flush();
        return te;
    }

    /**
     * Method to update a Te in the DB
     * @param te
     * @param em
     * @return a TE
     */
    public TeEntity updateTe(TeEntity te, EntityManager em){
        em.merge(te);
        return te;
    }
}
