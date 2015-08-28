package org.workshop1.database;

import java.util.ArrayList;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.workshop1.model.Klant;
import org.workshop1.model.Adres;

public class KlantDaoXml implements KlantDao {
    public static final String DEFAULT_LOCATION = "data/xml";
    private File directory;
    private XMLEncoder encoder;
    
    public KlantDaoXml() throws DaoConfigurationException {
        this(DEFAULT_LOCATION);
    }
    
    public KlantDaoXml(String location) throws DaoConfigurationException {
        directory = new File(location);
        if(!directory.exists())
            directory.mkdirs();
        else if(!directory.isDirectory())
            throw new DaoConfigurationException(location + "is geen geldige locatie.");
    }
    
    public Klant read(int klant_id) throws DaoException {
        
    }
    
    public Klant read(String voornaam) throws DaoException {
        
    }
    
    public Klant read(String voornaam, String achternaam) throws DaoException {
        
    }
    
    public Klant read(Adres adres) throws DaoException {
    
    }
    
    public Klant read(String postcode, int huisnummer) throws DaoException {
        
    }
    
    public ArrayList<Klant> readAll() throws DaoException {
        
    }
    
    public void add(Klant klant) throws DaoException {
        File klantFile = new File(directory.getPath() + "/klant" + klant.getKlant_id() + ".xml");
        try(XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(klantFile)))) {
            encoder.writeObject(klant);
        }
        catch(FileNotFoundException ex) {
            throw new DaoException("Schrijven naar " + klantFile + " is mislukt", ex);
        }
    }
    
    public void update(Klant klant) throws DaoException {
        add(klant);
    }
    
    public void delete(Klant klant) throws DaoException {
        
    }
}
