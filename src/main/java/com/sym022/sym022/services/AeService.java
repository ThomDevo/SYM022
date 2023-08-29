package com.sym022.sym022.services;

import com.sym022.sym022.entities.AeEntity;

import javax.persistence.EntityManager;

public class AeService {

    /**
     * Method to find an Ae based on the id
     * @param idAe
     * @param em
     * @return an Ae
     */
    public AeEntity findAeById(int idAe, EntityManager em){
        return em.createNamedQuery("Ae.selectAeById", AeEntity.class)
                .setParameter("idAe",idAe)
                .getSingleResult();
    }

    /**
     * Method to add an Ae in the DB
     * @param ae
     * @param em
     * @return an Ae
     */
    public AeEntity addAe(AeEntity ae, EntityManager em){
        em.persist(ae);
        em.flush();
        return ae;
    }

    /**
     * Method to update an Ae in the DB
     * @param ae
     * @param em
     * @return an Ae
     */
    public AeEntity updateAe(AeEntity ae, EntityManager em){
        em.merge(ae);
        return ae;
    }
}
