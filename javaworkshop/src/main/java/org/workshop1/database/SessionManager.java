package org.workshop1.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionManager {
    
    private static SessionFactory sessionFactory = null;
    private static boolean isInitialized = false;
    
    private SessionManager() {}
    
    public static void initialize(Configuration cfg) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        isInitialized = true;
    }
    
    public static SessionFactory getSessionFactory() throws IllegalStateException {
        if(isInitialized)
            return sessionFactory;
        else
            throw new IllegalStateException("SessionManager has not been initialized.");
    }
}
