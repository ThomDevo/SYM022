package com.sym022.sym022.services;

import com.sym022.sym022.entities.CmEntity;

import javax.persistence.EntityManager;

public class CmService {
    /**
     * Method to find a Cm based on the id
     * @param idCm
     * @param em
     * @return a Cm
     */
    public CmEntity findCmById(int idCm, EntityManager em){
        return em.createNamedQuery("Cm.selectCmById", CmEntity.class)
                .setParameter("idCm",idCm)
                .getSingleResult();
    }

    /**
     * Method to add a Cm in the DB
     * @param cm
     * @param em
     * @return a cm
     */
    public CmEntity addCm(CmEntity cm, EntityManager em){
        em.persist(cm);
        em.flush();
        return cm;
    }

    /**
     * Method to update a Cm in the DB
     * @param cm
     * @param em
     * @return a cm
     */
    public CmEntity updateCm(CmEntity cm, EntityManager em){
        em.merge(cm);
        return cm;
    }
}
