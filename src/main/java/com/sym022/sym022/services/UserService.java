package com.sym022.sym022.services;

import com.sym022.sym022.entities.UserEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserService {

    /**
     * Method to find a user by username
     * @param username
     * @param em
     * @return User and a status : TRUE
     */
    public UserEntity findUserByUsername (String username, EntityManager em) {
        return em.createNamedQuery("User.SelectUser", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    /**
     * Method to know if a user exists in the database
     * @param username
     * @param em
     * @return True if user exists
     */
    public boolean isUserExist(String username, EntityManager em){
        Query query =em.createNamedQuery("User.IsUserExist", UserEntity.class);
        query.setParameter("username", username);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find all users
     * @param em
     * @return List of Users
     */
    public List<UserEntity> findUserAll(EntityManager em) {
        return em.createNamedQuery("User.SelectUserAll",UserEntity.class)
                .getResultList();
    }

    /**
     * Method to find a User based on the id
     * @param idUser
     * @param em
     * @return User
     */
    public UserEntity findUserById(int idUser, EntityManager em) {
        return em.createNamedQuery("User.findUserById", UserEntity.class)
                .setParameter("idUser", idUser)
                .getSingleResult();
    }

    /**
     * Method to find User ALL based on a filter and order by ASC
     * @param researchWord
     * @param em
     * @return List of Users
     */
    public List<UserEntity> findUserByFilterAndOrderAsc(String researchWord, EntityManager em){
        return em.createNamedQuery("User.FindUserByCharacteristic", UserEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to add a new user
     * @param user
     * @param em
     * @return user
     */
    public UserEntity addUser(UserEntity user, EntityManager em){
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Method to update a user
     * @param user
     * @param em
     * @return user
     */
    public UserEntity updateUser(UserEntity user, EntityManager em){
        em.merge(user);
        return user;
    }
}
