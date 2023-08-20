package com.sym022.sym022.services;

import com.sym022.sym022.entities.DovEntity;

import javax.persistence.EntityManager;

public class DovService {

    /**
     * Method to find a DOV based on the id
     * @param idDov
     * @param em
     * @return a DOV
     */
    public DovEntity findDovById(int idDov, EntityManager em)
    {
        return em.createNamedQuery("Dov.selectDovById",DovEntity.class)
                .setParameter("idDov",idDov)
                .getSingleResult();
    }

    /**
     * Method to add a DOV in the DB
     * @param dov
     * @param em
     * @return a DOV
     */
    public DovEntity addDov (DovEntity dov, EntityManager em){
        em.persist(dov);
        em.flush();
        return dov;
    }

    /**
     * Method to update a DOV in the DB
     * @param dov
     * @param em
     * @return a DOV
     */
    public DovEntity updateDov (DovEntity dov, EntityManager em){
        em.merge(dov);
        return dov;
    }
}
