package com.sym022.sym022.services;

import com.sym022.sym022.entities.AuditTrailEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class AuditTrailService {

    /**
     * Method to find an AuditTrail based on the id
     * @param idAuditTrail
     * @param em
     * @return an AuditTrail
     */
    public AuditTrailEntity findAuditTrailById(int idAuditTrail, EntityManager em){
        return em.createNamedQuery("AuditTrail.selectAuditTrailById", AuditTrailEntity.class)
                .setParameter("idAuditTrail", idAuditTrail)
                .getSingleResult();
    }

    /**
     * Method to find AuditTrail from permitted sites  based on a filter and order DESC
     * @param idUser
     * @param researchWord
     * @param em
     * @return List of AuditTrails
     */
    public List<AuditTrailEntity> findAuditTrailByFilterAndOrderDesc(int idUser, String researchWord, EntityManager em){
        return em.createNamedQuery("AuditTrail.findAuditTrailByCharacteristic", AuditTrailEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord)
                .getResultList();
    }

    /**
     * Method to add a new AuditTrail in the DB
     * @param auditTrail
     * @param em
     * @return an AuditTrail
     */
    public AuditTrailEntity addAuditTrail (AuditTrailEntity auditTrail, EntityManager em){
        em.persist(auditTrail);
        em.flush();
        return auditTrail;
    }

    /**
     * Method to update an AuditTrail in the DB
     * @param auditTrail
     * @param em
     * @return an AuditTrail
     */
    public AuditTrailEntity updateAuditTrail (AuditTrailEntity auditTrail, EntityManager em){
        em.merge(auditTrail);
        return auditTrail;
    }
}
