package com.sym022.sym022.services;

import com.sym022.sym022.entities.PermissionEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class PermissionService {

    /**
     * Permission request method by permissionLabel
     * @param permissionLabel
     * @return List of permission
     */
    public PermissionEntity findPermissionByPermissionLabel(String permissionLabel, EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionByLabel", PermissionEntity.class)
                .setParameter("permissionLabel", permissionLabel)
                .getSingleResult();
    }


    /**
     * Find permission by id of permission
     * @param idPermission
     * @param em
     * @return permission
     */
    public PermissionEntity findPermissionById(int idPermission, EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionById", PermissionEntity.class)
                .setParameter("idPermission", idPermission)
                .getSingleResult();
    }



    /**
     * Role request method for all
     */
    public List<PermissionEntity> findPermissionAll (EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionAll", PermissionEntity.class)
                .getResultList();
    }


    /**
     * Method to add a permission
     * @param permission
     * @param em
     * @return permission
     */
    public PermissionEntity addPermission (PermissionEntity permission, EntityManager em){
        em.persist(permission);
        em.flush();
        return permission;
    }


    /**
     * Method to update a permission
     * @param permission
     * @param em
     * @return permission
     */
    public PermissionEntity updatePermission (PermissionEntity permission, EntityManager em){
        em.merge(permission);
        return permission;
    }

}
