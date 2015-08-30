package org.workshop1.dao;

import java.util.ArrayList;
import org.workshop1.model.Klant;
import org.workshop1.model.Adres;

public interface KlantDao {
    public static final String DEFAULT_LOCATION_XML = "data/xml";
    
    public Klant read(int klant_id) throws DaoException;
    public Klant read(String voornaam) throws DaoException;
    public Klant read(String voornaam, String achternaam) throws DaoException;
    public Klant read(Adres adres) throws DaoException;
    public Klant read(String postcode, int huisnummer) throws DaoException;
    public ArrayList<Klant> readAll() throws DaoException;
    
    public void add(Klant klant) throws DaoException;
    
    public void update(Klant klant) throws DaoException;
    
    public void delete (int klant_id) throws DaoException;
    public void delete(Klant klant) throws DaoException;
}
