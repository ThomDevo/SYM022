package com.sym022.sym022.services;

import com.sym022.sym022.entities.DmEntity;
import com.sym022.sym022.entities.RoleEntity;

import javax.persistence.EntityManager;

public class DmService {

    /**
     * Method to find a DM based on idDm
     * @param idDm
     * @param em
     * @return a DM based on idDm
     */
    public DmEntity findDmById(int idDm, EntityManager em){
        return em.createNamedQuery("Dm.selectDmById", DmEntity.class)
                .setParameter("idDm", idDm)
                .getSingleResult();
    }

    /**
     * Method to add a DM in the DB
     * @param dm
     * @param em
     * @return a DM
     */
    public DmEntity addDm(DmEntity dm, EntityManager em){
        em.persist(dm);
        em.flush();
        return dm;
    }

    /**
     * Method to update a DM in the DB
     * @param dm
     * @param em
     * @return
     */
    public DmEntity updateDm(DmEntity dm, EntityManager em){
        em.merge(dm);
        return dm;
    }
}
