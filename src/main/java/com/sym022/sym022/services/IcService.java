package com.sym022.sym022.services;

import com.sym022.sym022.entities.IcEntity;
import com.sym022.sym022.entities.VisitEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class IcService
{
    /**
     * Method to find an Ic by id
     * @param idIc
     * @param em
     * @return Ic
     */
    public IcEntity findIcById(int idIc, EntityManager em)
    {
        return em.createNamedQuery("Ic.selectIcById", IcEntity.class)
                .setParameter("idIc", idIc)
                .getSingleResult();
    }

    /**
     * Method to find all IC where eligible is TRUE
     * @param em
     * @return
     */
    public List<VisitEntity> findIcEligibleYes (EntityManager em)
    {
        return em.createNamedQuery("Ic.icEligibleYes", VisitEntity.class)
                .getResultList();
    }

    /**
     * Method to add an IC in the DB
     * @param ic
     * @param em
     * @return an IC
     */
    public IcEntity addIc (IcEntity ic, EntityManager em){
        em.persist(ic);
        em.flush();
        return ic;
    }

    /**
     * Method to update an IC in the DB
     * @param ic
     * @param em
     * @return an IC
     */
    public IcEntity updateIc (IcEntity ic, EntityManager em){
        em.merge(ic);
        return ic;
    }
}
