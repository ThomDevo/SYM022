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
    public boolean isFormLabelExist(int formLabel, EntityManager em){
        Query query =em.createNamedQuery("Form.isFormLabelExist", FormEntity.class);
        query.setParameter("formLabel", formLabel);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }
}
