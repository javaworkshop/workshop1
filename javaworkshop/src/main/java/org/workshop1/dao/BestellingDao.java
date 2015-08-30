package org.workshop1.dao;

import org.workshop1.dao.DaoException;
import java.util.ArrayList;
import org.workshop1.model.Bestelling;

public interface BestellingDao {
    public Bestelling read(int bestelling_id) throws DaoException;
    public ArrayList<Bestelling> readAll() throws DaoException;
    
    public void add(Bestelling bestelling) throws DaoException;
            
    public void update(Bestelling bestelling) throws DaoException;
    
    public void delete(int bestelling_id) throws DaoException;
    public void delete(Bestelling bestelling) throws DaoException;
}
