package com.sym022.sym022.services;

import com.sym022.sym022.entities.DovEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
     * Method to find a DOV based on the idEvent
     * @param idEvent
     * @param em
     * @return a DOV
     */
    public DovEntity findDovByIdEvent(int idEvent, EntityManager em)
    {
        return em.createNamedQuery("Dov.selectDovByIdEvent",DovEntity.class)
                .setParameter("idEvent",idEvent)
                .getSingleResult();
    }

    /**
     * Method to find Dov Mois_1 Not done
     * @param idSubject
     * @param em
     * @return boolean
     */
    public boolean findDovNDMois1 (int idSubject, EntityManager em)
    {
        Query query =em.createNamedQuery("Dov.selectDovNDMois1", DovEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
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
