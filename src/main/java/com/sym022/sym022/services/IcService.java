package com.sym022.sym022.services;

import com.sym022.sym022.entities.IcEntity;
import com.sym022.sym022.entities.VisitEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
     * @param idSubject
     * @param em
     * @return
     */
    public boolean findIcEligibleYes (int idSubject, EntityManager em)
    {
        Query query =em.createNamedQuery("Ic.icEligibleYes", IcEntity.class);
        query.setParameter("idSubject", idSubject);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find all IC where eligible is FALSE
     * @param idSubject
     * @param em
     * @return
     */
    public List<IcEntity> findIcEligibleNo (int idSubject, EntityManager em)
    {
        return em.createNamedQuery("Ic.icEligibleNo", IcEntity.class)
                .setParameter("idSubject", idSubject)
                .getResultList();
    }

    /**
     * Method to find all IC based on idEvent
     * @param idEvent
     * @param em
     * @return
     */
    public IcEntity findIcByIdEvent (int idEvent, EntityManager em)
    {
        return em.createNamedQuery("Ic.selectIcByIdEvent", IcEntity.class)
                .setParameter("idEvent", idEvent)
                .getSingleResult();
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
