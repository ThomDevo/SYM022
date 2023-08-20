package com.sym022.sym022.services;

import com.sym022.sym022.entities.SubjectEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SubjectService {

    /**
     * Method to find all subject
     * @param em
     * @return List of subjects
     */
    public List<SubjectEntity> findSubjectAll (EntityManager em)
    {
        return em.createNamedQuery("Subject.selectSubjectAll", SubjectEntity.class)
                .getResultList();
    }

    /**
     * Method to find a subject by id
     * @param idSubject
     * @param em
     * @return a subject
     */
    public SubjectEntity findSubjectById(int idSubject, EntityManager em)
    {
        return em.createNamedQuery("Subject.selectSubjectById", SubjectEntity.class)
                .setParameter("idSubject", idSubject)
                .getSingleResult();
    }

    /**
     * Method to find a subject by subject number
     * @param subjectNum
     * @param em
     * @return a subject
     */
    public SubjectEntity findSubjectByNum(int subjectNum, EntityManager em)
    {
        return em.createNamedQuery("Subject.selectSubjectByNum", SubjectEntity.class)
                .setParameter("subjectNum", subjectNum)
                .getSingleResult();
    }

    /**
     * Method to check if a Subject Number already exists
     * @param subjectNum
     * @param em
     * @return boolean
     */
    public boolean isSubjectExist(int subjectNum, EntityManager em){
        Query query =em.createNamedQuery("Subject.isSubjectNumExist", SubjectEntity.class);
        query.setParameter("subjectNum", subjectNum);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find Subject ALL based on a filter and order by ASC
     * @param researchWord
     * @param em
     * @return List of Subjects
     */
    public List<SubjectEntity> findAllSubjectByFilterAndOrderAsc(String researchWord, EntityManager em){
        return em.createNamedQuery("Subject.findAllSubjectByCharacteristic",SubjectEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find Subject ALL and ACTIVE based on a filter and order by ASC
     * @param researchWord
     * @param em
     * @return List of Subjects
     */
    public List<SubjectEntity> findSubjectActiveByFilterAndOrderAsc(String researchWord, EntityManager em){
        return em.createNamedQuery("Subject.findSubjectActiveByCharacteristic",SubjectEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to get access only to permitted subjects
     * @param idUser
     * @param em
     * @return List of subjects
     */
    public List<SubjectEntity> findSubjectPermitted (int idUser, EntityManager em)
    {
        return em.createNamedQuery("Subject.selectSubjectPermitted", SubjectEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to add a new subject
     * @param subject
     * @param em
     * @return subject
     */
    public SubjectEntity addSubject(SubjectEntity subject, EntityManager em){
        em.persist(subject);
        em.flush();
        return subject;
    }

    /**
     * Method to update a subject
     * @param subject
     * @param em
     * @return subject
     */
    public SubjectEntity updateSubject(SubjectEntity subject, EntityManager em){
        em.merge(subject);
        return subject;
    }
}
