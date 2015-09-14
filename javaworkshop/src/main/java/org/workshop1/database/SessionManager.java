package org.workshop1.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionManager {
    
    private static EntityManagerFactory emFactory = null;
    private static SessionFactory sessionFactory = null;
    private static boolean isInitialized = false;
    
    private SessionManager() {}
    
    public static void initialize(Configuration cfg) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        
        try {
        	emFactory = Persistence.createEntityManagerFactory("workshop1");
        }
        catch(PersistenceException ex) {
        	System.err.println(ex.getMessage());
        	ex.printStackTrace();
        }
        
        isInitialized = true;
    }
    
    public static SessionFactory getSessionFactory() throws IllegalStateException {
        if(isInitialized)
            return sessionFactory;
        else
            throw new IllegalStateException("SessionManager has not been initialized.");
    }
    
    public static EntityManagerFactory getEntityManagerFactory() throws IllegalStateException {
        if(isInitialized)
            return emFactory;
        else
            throw new IllegalStateException("SessionManager has not been initialized.");
    }
}
