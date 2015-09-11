package org.workshop1.dao;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.workshop1.database.SessionManager;
import org.workshop1.model.Bestelling;
import org.workshop1.model.Klant;

public class BestellingDaoHibernate implements BestellingDao {

    @Override
    public void add(Bestelling bestelling) throws DaoException {
        try (Session session = SessionManager.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();            
            session.load(bestelling.getKlant(), bestelling.getKlant().getKlant_id());
            //bestelling.setKlant((Klant)session.merge(bestelling.getKlant())); Dit kan ook ipv regel hierboven
            session.persist(bestelling);
            tx.commit();
        }
    }

    @Override
    public void close() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int bestelling_id) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Bestelling bestelling) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bestelling read(int bestelling_id) throws DaoException {
        try(Session session = SessionManager.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Bestelling bestelling = (Bestelling) session.byId(Bestelling.class).load(bestelling_id);
            tx.commit();
            return bestelling;
        }
    }

    @Override
    public ArrayList<Bestelling> readAll() throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Bestelling bestelling) throws DaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
