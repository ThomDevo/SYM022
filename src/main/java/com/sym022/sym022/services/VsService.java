package com.sym022.sym022.services;

import com.sym022.sym022.entities.VsEntity;

import javax.persistence.EntityManager;

public class VsService {

    /**
     * Method to find a VS based on idVs
     * @param idVs
     * @param em
     * @return VS
     */
    public VsEntity findVsById (int idVs, EntityManager em){
        return em.createNamedQuery("Vs.selectVsById", VsEntity.class)
                .setParameter("idVs", idVs)
                .getSingleResult();
    }

    /**
     * Method to find a VS based on idVs
     * @param idEvent
     * @param em
     * @return VS
     */
    public VsEntity findVsByIdEvent (int idEvent, EntityManager em){
        return em.createNamedQuery("Vs.selectVsByIdEvent", VsEntity.class)
                .setParameter("idEvent", idEvent)
                .getSingleResult();
    }

    /**
     * Method to add a VS in the DB
     * @param vs
     * @param em
     * @return a VS
     */
    public VsEntity addVs (VsEntity vs, EntityManager em){
        em.persist(vs);
        em.flush();
        return vs;
    }

    /**
     * Method to update a VS in the DB
     * @param vs
     * @param em
     * @return a VS
     */
    public VsEntity updateVs (VsEntity vs, EntityManager em){
        em.merge(vs);
        return vs;
    }
}
