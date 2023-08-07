package com.sym022.sym022.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("sym022");

    private EMF() {
    }

    public static EntityManagerFactory getEMF() {
        return emfInstance;
    }

    public static EntityManager getEM() {
        return emfInstance.createEntityManager();
    }

    /*	Create EntityManager in others classes
     * EntityManager em = EMF.getEM();
     * try {
     *     // ... do stuff with em ...
     * } finally {
     *     em.close();
     * }
     */
}
