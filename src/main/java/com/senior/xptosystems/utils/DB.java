package com.senior.xptosystems.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DB {

    private final EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager = null;

    public DB() {
        entityManagerFactory = Persistence.createEntityManagerFactory("xptosystemsPU");
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
