/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.xptosystems.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rtools2
 */
public class DB {

    private EntityManagerFactory entityManagerFactory;

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
