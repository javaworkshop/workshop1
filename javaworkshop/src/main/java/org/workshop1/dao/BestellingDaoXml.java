package org.workshop1.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import org.workshop1.model.Bestelling;
import org.workshop1.model.Data;

public class BestellingDaoXml implements BestellingDao {
    private File bestellingFile;
    XStream xStream;
    
    public BestellingDaoXml() throws DaoConfigurationException {
        this(Dao.DEFAULT_LOCATION_XML);
    }
    
    public BestellingDaoXml(String folder) throws DaoConfigurationException {        
        xStream = new XStream(new StaxDriver());
        
        File directory = new File(folder);
        if(!directory.exists())
            directory.mkdirs();
        else if(!directory.isDirectory())
            throw new DaoConfigurationException(folder + "is geen geldige map.");
        
        bestellingFile = new File(directory.getPath() + "/bestelling.xml");
        
        xStream.alias("klant", Bestelling.class);
        xStream.registerConverter(new DataConverter(), XStream.PRIORITY_VERY_HIGH);
    }
    
    @Override
    public Bestelling read(int bestelling_id) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(bestellingFile))) {            
            while(true) {
                Bestelling b = (Bestelling)klantInputStream.readObject();
                if(b.getBestelling_id() == bestelling_id)
                    return b;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + bestellingFile + " is mislukt", ex);
        }
        
        return null;
    }
    
    @Override
    public ArrayList<Bestelling> readAll() throws DaoException {
        ArrayList<Bestelling> bestellingen = new ArrayList<>();
        
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(bestellingFile))) {            
            while(true) {
                Bestelling b = (Bestelling)klantInputStream.readObject();
                bestellingen.add(b);
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + bestellingFile + " is mislukt", ex);
        }
        
        return bestellingen;
    }
    
    @Override
    public void add(Bestelling bestelling) throws DaoException {
        if(bestellingFile.exists()) {
            ArrayList<Bestelling> bestellingen = readAll();
            Collections.sort(bestellingen);
            if(Data.indexOfPrimaryKey(bestellingen, bestelling.getBestelling_id()) < 0) {
                bestellingen.add(bestelling);
                write(bestellingen);
            }
        }
        else
            write(bestelling);   
    }
            
    @Override
    public void update(Bestelling bestelling) throws DaoException {
        ArrayList<Bestelling> bestellingen = readAll();
        int index = Data.indexOfPrimaryKey(bestellingen, bestelling.getBestelling_id());
        if(index > -1) {
            bestellingen.set(index, bestelling);
            write(bestellingen);
        }
    }
    
    @Override
    public void delete(int bestelling_id) throws DaoException {
        ArrayList<Bestelling> bestellingen = readAll();
        int index = Data.indexOfPrimaryKey(bestellingen, bestelling_id);
        if(index > -1) {
            bestellingen.remove(index);
            write(bestellingen);
        }
    }
    
    @Override
    public void delete(Bestelling bestelling) throws DaoException {
        delete(bestelling.getBestelling_id());
    }
    
    @Override
    public void close() {
        // nothing happens...
    }
    
    // Helpers -------------------------------------------------------------------------------------
    
    private void write(ArrayList<Bestelling> bestellingen) {
        try(ObjectOutputStream klantOutputStream = xStream.createObjectOutputStream(
                new PrettyPrintWriter(new FileWriter(bestellingFile)), "klanten")) {
            for(Bestelling b : bestellingen) {
                klantOutputStream.writeObject(b);
            }
        }
        catch(IOException ex) {
            throw new DaoException("Schrijven naar " + bestellingFile + " is mislukt", ex);
        }
    }
    
    private void write(Bestelling bestelling) {
        try(ObjectOutputStream klantOutputStream = xStream.createObjectOutputStream(
                new PrettyPrintWriter(new FileWriter(bestellingFile)), "klanten")) {
            klantOutputStream.writeObject(bestelling);
        }
        catch(IOException ex) {
            throw new DaoException("Schrijven naar " + bestellingFile + " is mislukt", ex);
        }
    }    
}
