package com.sym022.sym022.services;

import com.sym022.sym022.entities.RoleEntity;
import com.sym022.sym022.utilities.ProcessUtils;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RoleService {

    /**
     * Role request method by roleLabel
     * @param roleLabel
     * @return List of roles
     */
    public RoleEntity findRoleByRoleLabel(String roleLabel, EntityManager em)
    {
        return em.createNamedQuery("Role.selectRoleByRoleLabel", RoleEntity.class)
                .setParameter("roleLabel", roleLabel)
                .getSingleResult();
    }


    /**
     * Find role by id of role
     * @param idRole
     * @param em
     * @return a role
     */
    public RoleEntity findRoleById(int idRole, EntityManager em)
    {
        return em.createNamedQuery("Role.selectRoleById", RoleEntity.class)
                .setParameter("idRole", idRole)
                .getSingleResult();
    }



    /**
     * Role request method for all
     */
    public List<RoleEntity> findRoleAll (EntityManager em)
    {
        return em.createNamedQuery("Role.selectRoleAll", RoleEntity.class)
                .getResultList();
    }

    /**
     * Method to have a List of all roles with no permissions
     * @param em
     * @return a List of roles
     */
    public List<RoleEntity> findRoleAllEmptyPermissions (EntityManager em)
    {
        return em.createNamedQuery("Role.selectRoleAllEmpty", RoleEntity.class)
                .getResultList();
    }


    /**
     * Method to filter the whole list of roles
     * @param researchWord
     * @param em
     * @return List of roles
     */
    public List<RoleEntity> findRoleByFilter (String researchWord,EntityManager em){
        return em.createNamedQuery("Role.selectAllRoleFilter", RoleEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }


    /**
     * Method to check if role exists
     * @param roleLabel
     * @param em
     * @return boolean
     */
    public boolean isRoleExist(String roleLabel, EntityManager em){
        Query query = em.createNamedQuery("Role.isRoleExist", RoleEntity.class);
        query.setParameter("roleLabel", roleLabel);

        int count = ((Number)query.getSingleResult()).intValue();
        return count > 0;
    }


    /**
     * Method to add a role
     * @param role
     * @param em
     * @return role
     */
    public RoleEntity addRole (RoleEntity role, EntityManager em){
        em.persist(role);
        em.flush();
        //ProcessUtils.debug(String.valueOf(role.getIdRole()));
        return role;
    }


    /**
     * Method to update a role
     * @param role
     * @param em
     * @return role
     */
    public RoleEntity updateRole (RoleEntity role, EntityManager em){
        em.merge(role);
        return role;
    }
}
