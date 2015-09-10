package org.workshop1.dao;

import java.util.ArrayList;
import org.hibernate.Session;
import org.workshop1.database.SessionManager;
import org.hibernate.Transaction;
import org.workshop1.model.Adres;
import org.workshop1.model.Klant;

public class KlantDaoHibernate implements KlantDao {
    
    public KlantDaoHibernate() throws DaoConfigurationException {
        
    }

    @Override
    public Klant read(int klant_id) throws DaoException {
        try(Session session = SessionManager.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Klant klant = (Klant) session.byId(Klant.class).load(klant_id);
            tx.commit();
            return klant;
        }
    }

    @Override
    public ArrayList<Klant> read(String voornaam) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Klant> read(String voornaam, String achternaam) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Klant> read(Adres adres) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Klant> read(String postcode, int huisnummer) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Klant> readAll() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Klant klant) throws DaoException {
        try (Session session = SessionManager.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(klant);
            tx.commit();
        }
    }

    @Override
    public void update(Klant klant) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int klant_id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Klant klant) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
