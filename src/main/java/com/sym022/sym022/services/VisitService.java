package com.sym022.sym022.services;

import com.sym022.sym022.entities.VisitEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VisitService {

    /**
     * Method to find all visits
     * @param em
     * @return List of visits
     */
    public List<VisitEntity> findVisitAll (EntityManager em)
    {
        return em.createNamedQuery("Visit.selectVisitAll", VisitEntity.class)
                .getResultList();
    }

    /**
     * Method to find a visit by id
     * @param idVisit
     * @param em
     * @return visit
     */
    public VisitEntity findVisitById(int idVisit, EntityManager em)
    {
        return em.createNamedQuery("Visit.selectVisitById", VisitEntity.class)
                .setParameter("idVisit", idVisit)
                .getSingleResult();
    }

    /**
     * Method to find a visit by visit number
     * @param visitNum
     * @param em
     * @return visit
     */
    public VisitEntity findVisitByNum(int visitNum, EntityManager em)
    {
        return em.createNamedQuery("Visit.selectVisitByNum", VisitEntity.class)
                .setParameter("visitNum", visitNum)
                .getSingleResult();
    }

    /**
     * Method to find a visit by visit label
     * @param visitLabel
     * @param em
     * @return visit
     */
    public VisitEntity findVisitByLabel(int visitLabel, EntityManager em)
    {
        return em.createNamedQuery("Visit.selectVisitByLabel", VisitEntity.class)
                .setParameter("visitLabel", visitLabel)
                .getSingleResult();
    }

    /**
     * Method to check if a Visit Number already exists
     * @param visitNum
     * @param em
     * @return boolean
     */
    public boolean isVisitNumExist(int visitNum, EntityManager em){
        Query query =em.createNamedQuery("Visit.isVisitNumExist", VisitEntity.class);
        query.setParameter("visitNum", visitNum);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * * Method to check if a Visit Label already exists
     * @param visitLabel
     * @param em
     * @return boolean
     */
    public boolean isVisitLabelExist(int visitLabel, EntityManager em){
        Query query =em.createNamedQuery("Visit.isVisitLabelExist", VisitEntity.class);
        query.setParameter("visitLabel", visitLabel);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }
}
