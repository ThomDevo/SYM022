package com.sym022.sym022.services;

import com.sym022.sym022.entities.QueryEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class QueryService {

    /**
     * Method to consult a list of Queries
     * @param idUser
     * @param researchWord
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findConsultQuery (int idUser, String researchWord, EntityManager em)
    {
        return em.createNamedQuery("Query.selectConsult", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to get Opened Queries for DM
     * @param idUser
     * @param researchWord
     * @param em
     * @return List of visits
     */
    public List<QueryEntity> findOpenedDM(int idUser, String researchWord, EntityManager em)
    {
        return em.createNamedQuery("Query.selectOpenedDM", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to get Opened Queries for Role other than DM
     * @param idUser
     * @param idRole
     * @param researchWord
     * @param em
     * @return List of visits
     */
    public List<QueryEntity> findOpenedRole(int idUser ,int idRole, String researchWord,  EntityManager em)
    {
        return em.createNamedQuery("Query.selectOpenedRole", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("idRole", idRole)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to get Opened Queries for DM
     * @param idUser
     * @param researchWord
     * @param em
     * @return List of visits
     */
    public List<QueryEntity> findAnsweredDM(int idUser, String researchWord, EntityManager em)
    {
        return em.createNamedQuery("Query.selectAnsweredDM", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to get Opened Queries for Role other than DM
     * @param idUser
     * @param idRole
     * @param researchWord
     * @param em
     * @return List of visits
     */
    public List<QueryEntity> findAnsweredRole(int idUser,int idRole, String researchWord, EntityManager em)
    {
        return em.createNamedQuery("Query.selectAnsweredRole", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("idRole", idRole)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }


    /**
     * Method to add a QUERY in the DB
     * @param query
     * @param em
     * @return
     */
    public QueryEntity addQuery (QueryEntity query, EntityManager em){
        em.persist(query);
        em.flush();
        return query;
    }

    /**
     * Method to update a QUERY in the DB
     * @param query
     * @param em
     * @return
     */
    public QueryEntity updateQuery (QueryEntity query, EntityManager em){
        em.merge(query);
        return query;
    }
}
