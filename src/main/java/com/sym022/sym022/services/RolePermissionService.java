package com.sym022.sym022.services;

import com.sym022.sym022.entities.RolePermissionEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class RolePermissionService {

    /**
     * Method to find all RolePermission
     * @param em
     * @return List of role permissions
     */
    public List<RolePermissionEntity> findAll(String researchWord, EntityManager em) {
        return em.createNamedQuery("RolePermission.SelectAll", RolePermissionEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all RolePermission based on the idRole
     * @param roleId
     * @param em
     * @return List of RolePermissions
     */
    public List<RolePermissionEntity> findRolePermissionByIdRole(int roleId, EntityManager em) {
        return em.createNamedQuery("RolePermission.SelectListPermissionByIdRole", RolePermissionEntity.class)
                .setParameter("idRole", roleId)
                .getResultList();
    }

    /**
     * Method to find the RolePermission based on the idRole and the idPermission
     * @param roleId
     * @param idPermission
     * @param em
     * @return RolePermission
     */
    public RolePermissionEntity findRolePermissionByIdRoleAndByIdPermission(int roleId, int idPermission, EntityManager em){
        return em.createNamedQuery("RolePermission.SelectListPermissionByIdRoleANdByIdPermission", RolePermissionEntity.class)
                .setParameter("idRole", roleId)
                .setParameter("idPermission", idPermission)
                .getSingleResult();
    }

    /**
     * Method to add a RolePermission
     * @param rolePermission
     * @param em
     * @return RolePermission
     */
    public RolePermissionEntity addRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        em.persist(rolePermission);
        em.flush();
        return rolePermission;
    }

    /**
     * Method to update a RolePermission
     * @param rolePermission
     * @param em
     * @return RolePermission
     */
    public RolePermissionEntity updateRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        em.merge(rolePermission);
        return rolePermission;
    }

    /**
     * Method to delete a RolePermission
     * @param rolePermission
     * @param em
     */
    public void deleteRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        if(!em.contains(rolePermission))
            rolePermission = em.merge(rolePermission);
        em.remove(rolePermission);
        em.flush();
    }
}
