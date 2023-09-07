package com.sym022.sym022.services;

import com.sym022.sym022.entities.QueryEntity;

import javax.persistence.EntityManager;

public class QueryService {

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
