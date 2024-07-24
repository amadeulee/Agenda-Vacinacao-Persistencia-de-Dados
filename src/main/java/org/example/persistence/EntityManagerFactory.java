package org.example.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
    private static EntityManager entityManagerInstance;

    private EntityManagerFactory() {
        // Evitar a criação de instâncias externamente
    }

    public static EntityManager getEntityManager() {
        if (entityManagerInstance == null) {
            synchronized (EntityManagerFactory.class) {
                if (entityManagerInstance == null) {
                    entityManagerInstance = createEntityManager();
                }
            }
        }
        return entityManagerInstance;
    }

    private static EntityManager createEntityManager() {
        var factory = Persistence.createEntityManagerFactory("vacinacaoElias");
        return factory.createEntityManager();
    }
}
