package com.sym022.sym022.services;

import com.sym022.sym022.entities.FormEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class FormService {

    /**
     * Method to find all forms
     * @param em
     * @return List of forms
     */
    public List<FormEntity> findFormAll (EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormAll", FormEntity.class)
                .getResultList();
    }

    /**
     * Method to find a form by id
     * @param idForm
     * @param em
     * @return form
     */
    public FormEntity findFormById(int idForm, EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormById", FormEntity.class)
                .setParameter("idForm", idForm)
                .getSingleResult();
    }

    /**
     * Method to find a Form by Form number
     * @param formNum
     * @param em
     * @return form
     */
    public FormEntity findFormByNum(int formNum, EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormByNum", FormEntity.class)
                .setParameter("formNum", formNum)
                .getSingleResult();
    }

    /**
     * Method to find a Form by Form label
     * @param formLabel
     * @param em
     * @return form
     */
    public FormEntity findFormByLabel(int formLabel, EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormByLabel", FormEntity.class)
                .setParameter("formLabel", formLabel)
                .getSingleResult();
    }

    /**
     * Method to check if a Form Number already exists
     * @param formNum
     * @param em
     * @return boolean
     */
    public boolean isFormNumExist(int formNum, EntityManager em){
        Query query =em.createNamedQuery("Form.isFormNumExist", FormEntity.class);
        query.setParameter("formNum", formNum);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * * Method to check if a Form Label already exists
     * @param formLabel
     * @param em
     * @return boolean
     */
    public boolean isFormLabelExist(String formLabel, EntityManager em){
        Query query =em.createNamedQuery("Form.isFormLabelExist", FormEntity.class);
        query.setParameter("formLabel", formLabel);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to get Ae form
     * @param em
     * @return List of forms = Ae
     */
    public List<FormEntity> findFormAe (EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormAe", FormEntity.class)
                .getResultList();
    }

    /**
     * Method to get Cm form
     * @param em
     * @return List of forms = Cm
     */
    public List<FormEntity> findFormCm (EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormCm", FormEntity.class)
                .getResultList();
    }

    /**
     * Method to get Dov form
     * @param em
     * @return List of forms = Dov
     */
    public List<FormEntity> findFormDov (EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormDov", FormEntity.class)
                .getResultList();
    }

    /**
     * Method to get Ic form
     * @param em
     * @return List of forms = Ic
     */
    public List<FormEntity> findFormIc (EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormIc", FormEntity.class)
                .getResultList();
    }

    /**
     * Method to find missing forms from Screening
     * @param idSubject
     * @param em
     * @return List of forms
     */
    public List<FormEntity> findFormScreeningND (int idSubject,EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormScreeningND", FormEntity.class)
                .setParameter("idSubject", idSubject)
                .getResultList();
    }

    /**
     * Method to find missing forms from Mois 1
     * @param idSubject
     * @param em
     * @return List of forms
     */
    public List<FormEntity> findFormMois1ND (int idSubject,EntityManager em)
    {
        return em.createNamedQuery("Form.selectFormMois1ND", FormEntity.class)
                .setParameter("idSubject", idSubject)
                .getResultList();
    }

    /**
     * Method to add a new form
     * @param form
     * @param em
     * @return form
     */
    public FormEntity addForm(FormEntity form, EntityManager em){
        em.persist(form);
        em.flush();
        return form;
    }

    /**
     * Method to update a form
     * @param form
     * @param em
     * @return form
     */
    public FormEntity updateForm(FormEntity form, EntityManager em){
        em.merge(form);
        return form;
    }
}
