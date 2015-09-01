package org.workshop1.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.QNameMap;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import org.workshop1.model.Klant;
import org.workshop1.model.Adres;
import org.workshop1.model.Data;


public class KlantDaoXml implements KlantDao {   
    private File klantFile;
    XStream xStream;
    
    public KlantDaoXml() throws DaoConfigurationException {
        this(KlantDao.DEFAULT_LOCATION_XML);
    }
    
    public KlantDaoXml(String folder) throws DaoConfigurationException {        
        xStream = new XStream(new StaxDriver());
        
        File directory = new File(folder);
        if(!directory.exists())
            directory.mkdirs();
        else if(!directory.isDirectory())
            throw new DaoConfigurationException(folder + "is geen geldige map.");
        
        klantFile = new File(directory.getPath() + "/klant.xml");
        
        xStream.alias("klant", Klant.class);
        xStream.registerConverter(new DataConverter(), XStream.PRIORITY_VERY_HIGH);
    }
  
    @Override
    public Klant read(int klant_id) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getKlant_id() == klant_id)
                    return k;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return null;
    }
    
    @Override
    public ArrayList<Klant> read(String voornaam) throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getVoornaam().equals(voornaam))
                    klanten.add(k);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return klanten;
    }
    
    @Override
    public ArrayList<Klant> read(String voornaam, String achternaam) throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getVoornaam().equals(voornaam) && k.getAchternaam().equals(achternaam))
                    klanten.add(k);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return klanten;
    }
    
    @Override
    public ArrayList<Klant> read(Adres adres) throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getAdres().equals(adres))
                    klanten.add(k);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return klanten;
    }
    
    @Override
    public ArrayList<Klant> read(String postcode, int huisnummer) throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getAdres().getPostcode().equals(postcode) 
                        && k.getAdres().getHuisnummer() == huisnummer)
                    klanten.add(k);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return klanten;
    }

    @Override
    public ArrayList<Klant> readAll() throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();
        
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                klanten.add(k);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return klanten;
    }
    
    @Override
    public void add(Klant klant) throws DaoException {
        if(klantFile.exists()) {
            ArrayList<Klant> klanten = readAll();
            Collections.sort(klanten);
            if(Data.indexOfPrimaryKey(klanten, klant.getKlant_id()) < 0) {
                klanten.add(klant);
                write(klanten);
            }
        }
        else
            write(klant);        
    }
  
    @Override
    public void update(Klant klant) throws DaoException {
        ArrayList<Klant> klanten = readAll();
        int index = Data.indexOfPrimaryKey(klanten, klant.getKlant_id());
        if(index > -1) {
            klanten.set(index, klant);
            write(klanten);
        }
    }
    
    @Override
    public void delete(int klant_id) throws DaoException {
        ArrayList<Klant> klanten = readAll();
        int index = Data.indexOfPrimaryKey(klanten, klant_id);
        if(index > -1) {
            klanten.remove(index);
            write(klanten);
        }
    }
    
    @Override
    public void delete(Klant klant) throws DaoException {
        ArrayList<Klant> klanten = readAll();
        int index = klanten.indexOf(klant.getKlant_id());
        if(index > -1) {
            klanten.remove(index);
            write(klanten);
        }
    }
    
    private void write(ArrayList<Klant> klanten) {
        try(ObjectOutputStream klantOutputStream = xStream.createObjectOutputStream(
                new PrettyPrintWriter(new FileWriter(klantFile)), "klanten")) {
            for(Klant k : klanten) {
                klantOutputStream.writeObject(k);
            }
        }
        catch(IOException ex) {
            throw new DaoException("Schrijven naar " + klantFile + " is mislukt", ex);
        }
    }
    
    private void write(Klant klant) {
        try(ObjectOutputStream klantOutputStream = xStream.createObjectOutputStream(
                new PrettyPrintWriter(new FileWriter(klantFile)), "klanten")) {
            klantOutputStream.writeObject(klant);
        }
        catch(IOException ex) {
            throw new DaoException("Schrijven naar " + klantFile + " is mislukt", ex);
        }
    }
    
    @Override
    public void close() {
        // nothing happens...
    }
}
