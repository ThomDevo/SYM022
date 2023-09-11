package com.sym022.sym022.services;

import com.sym022.sym022.entities.QueryEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class QueryService {

    /**
     * Method to consult a list of Queries
     * @param idUser
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findConsultQuery (int idUser, EntityManager em)
    {
        return em.createNamedQuery("Query.selectConsult", QueryEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to get Opened Queries for DM
     * @param idUser
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findOpenedDM(int idUser, EntityManager em)
    {
        return em.createNamedQuery("Query.selectOpenedDM", QueryEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to get Opened Queries for Role other than DM
     * @param idUser
     * @param idRole
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findOpenedRole(int idUser,int idRole,  EntityManager em)
    {
        return em.createNamedQuery("Query.selectOpenedRole", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("idRole", idRole)
                .getResultList();
    }

    /**
     * Method to get Opened Queries for DM
     * @param idUser
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findAnsweredDM(int idUser, EntityManager em)
    {
        return em.createNamedQuery("Query.selectAnsweredDM", QueryEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to get Opened Queries for Role other than DM
     * @param idUser
     * @param idRole
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findAnsweredRole(int idUser,int idRole,  EntityManager em)
    {
        return em.createNamedQuery("Query.selectAnsweredRole", QueryEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("idRole", idRole)
                .getResultList();
    }
    /**
     * Method to get list of Queries on an Event
     * @param idEvent
     * @param em
     * @return List of queries
     */
    public List<QueryEntity> findByEvent(int idEvent,  EntityManager em)
    {
        return em.createNamedQuery("Query.selectByEvent", QueryEntity.class)
                .setParameter("idEvent", idEvent)
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
