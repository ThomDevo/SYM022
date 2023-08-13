package com.sym022.sym022.services;

import com.sym022.sym022.entities.UserSiteEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class UserSiteService {

    /**
     * Method to find all UserSite
     * @param em
     * @return List of uUserSites
     */
    public List<UserSiteEntity> findAll(String researchWord, EntityManager em) {
        return em.createNamedQuery("UserSite.SelectAll", UserSiteEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all UserSite based on the idUser
     * @param idUser
     * @param em
     * @return List of UserSites
     */
    public List<UserSiteEntity> findUserSiteByIdUser(int idUser, EntityManager em) {
        return em.createNamedQuery("UserSite.SelectListSitesByIdUser", UserSiteEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to find the UserSite based on the idUser and the idSite
     * @param idUser
     * @param idSite
     * @param em
     * @return UserSite
     */
    public UserSiteEntity findUserSiteByIdUserAndByIdSite(int idUser, int idSite, EntityManager em){
        return em.createNamedQuery("UserSite.SelectListSitesByIdUserANdByIdUser", UserSiteEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("idSite", idSite)
                .getSingleResult();
    }

    /**
     * Method to add a UserSite
     * @param userSite
     * @param em
     * @return UserSite
     */
    public UserSiteEntity addUserSite(UserSiteEntity userSite, EntityManager em){
        em.persist(userSite);
        em.flush();
        return userSite;
    }

    /**
     * Method to update a UserSite
     * @param userSite
     * @param em
     * @return UserSite
     */
    public UserSiteEntity updateUserSite(UserSiteEntity userSite, EntityManager em){
        em.merge(userSite);
        return userSite;
    }

    /**
     * Method to delete a UserSite
     * @param userSite
     * @param em
     */
    public void deleteUserSite(UserSiteEntity userSite, EntityManager em){
        if(!em.contains(userSite))
            userSite = em.merge(userSite);
        em.remove(userSite);
        em.flush();
    }
}
