package org.workshop1.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
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
import org.workshop1.model.Klant;
import org.workshop1.model.Adres;
import org.workshop1.model.Data;


public class KlantDaoXml /*implements KlantDao*/ {   
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
        xStream.alias("adres", Adres.class);
        xStream.registerConverter(new KlantConverter(), XStream.PRIORITY_VERY_HIGH);
    }
  
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
    
    public Klant read(String voornaam) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getVoornaam().equals(voornaam))
                    return k;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return null;
    }
    
    public Klant read(String voornaam, String achternaam) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getVoornaam().equals(voornaam) && k.getAchternaam().equals(achternaam))
                    return k;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return null;
    }
    
    public Klant read(Adres adres) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getAdres().equals(adres))
                    return k;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return null;
    }
    
    public Klant read(String postcode, int huisnummer) throws DaoException {
        try(ObjectInputStream klantInputStream = xStream.createObjectInputStream(
                new FileInputStream(klantFile))) {            
            while(true) {
                Klant k = (Klant)klantInputStream.readObject();
                if(k.getAdres().getPostcode().equals(postcode) 
                        && k.getAdres().getHuisnummer() == huisnummer)
                    return k;
            }
        }
        catch(EOFException ex) {}
        catch(ClassNotFoundException | IOException ex) {
            throw new DaoException("Lezen van " + klantFile + " is mislukt", ex);
        }
        
        return null;
    }

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
    
    public void add(Klant klant) throws DaoException {
        if(klantFile.exists()) {
            ArrayList<Klant> klanten = readAll();
            Collections.sort(klanten);
            if(Data.indexOfPrimaryKey(klanten, klant) < 0) {
                klanten.add(klant);
                write(klanten);
            }
        }
        else
            write(klant);        
    }
  
    public void update(Klant klant) throws DaoException {
        ArrayList<Klant> klanten = readAll();
        int index = Data.indexOfPrimaryKey(klanten, klant);
        if(index > -1) {
            klanten.set(index, klant);
            write(klanten);
        }
    }
    
    public void delete(Klant klant) throws DaoException {
        ArrayList<Klant> klanten = readAll();
        int index = klanten.indexOf(klant);
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
    
    // het werkt zo wel, maar misschien is er een meer generieke converter van te maken mbv reflection (DataConverter)
    class KlantConverter implements Converter {
        @Override
        public boolean canConvert(Class object) {
            return object.equals(Klant.class);
   }
   
        @Override
        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            Klant klant = (Klant) value;
            writer.startNode("klant_id");
            if( klant.getKlant_id() == 0)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getKlant_id() + "");
            writer.endNode();
            writer.startNode("voornaam");
            if(klant.getVoornaam() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getVoornaam());
            writer.endNode();
            writer.startNode("tussenvoegsel");
            if(klant.getTussenvoegsel() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getTussenvoegsel());
            writer.endNode();
            writer.startNode("achternaam");
            if(klant.getAchternaam() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getAchternaam());
            writer.endNode();
            writer.startNode("email");
            if( klant.getEmail() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getEmail());
            writer.endNode();
            writer.startNode("adres");
            writer.startNode("straatnaam");
            if(klant.getStraatnaam() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getStraatnaam());
            writer.endNode();
            writer.startNode("huisnummer");
            if(klant.getHuisnummer() == 0)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getHuisnummer() + "");
            writer.endNode();
            writer.startNode("toevoeging");
            if(klant.getToevoeging() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getToevoeging());
            writer.endNode();
            writer.startNode("postcode");
            if(klant.getPostcode() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getPostcode());
            writer.endNode();
            writer.startNode("woonplaats");
            if(klant.getWoonplaats() == null)
                writer.setValue("NULL");
            else
                writer.setValue(klant.getWoonplaats());
            writer.endNode();
            writer.endNode();
        }
   
        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Klant klant = new Klant();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                if(reader.getNodeName().equals("klant_id"))
                    klant.setKlant_id(Integer.parseInt(reader.getValue()));
                else if(reader.getNodeName().equals("voornaam") && !(reader.getValue()).equals("NULL"))
                    klant.setVoornaam(reader.getValue());
                else if(reader.getNodeName().equals("tussenvoegsel") && !(reader.getValue().equals("NULL")))
                    klant.setTussenvoegsel(reader.getValue());
                else if(reader.getNodeName().equals("achternaam") && !(reader.getValue().equals("NULL")))
                    klant.setAchternaam(reader.getValue());
                else if(reader.getNodeName().equals("email") && !(reader.getValue().equals("NULL")))
                    klant.setEmail(reader.getValue());
                else if(reader.getNodeName().equals("adres")) {
                    Adres adres = new Adres();
                    klant.setAdres(adres);
                    while(reader.hasMoreChildren()) {
                        reader.moveDown();
                        String s = reader.getValue();
                        if(reader.getNodeName().equals("straatnaam") && !(reader.getValue().equals("NULL")))
                            adres.setStraatnaam(reader.getValue());
                        else if(reader.getNodeName().equals("huisnummer") && !(reader.getValue().equals("NULL")))
                            adres.setHuisnummer(Integer.parseInt(reader.getValue()));
                        else if(reader.getNodeName().equals("toevoeging") && !(reader.getValue().equals("NULL")))
                            adres.setToevoeging(reader.getValue());
                        else if(reader.getNodeName().equals("postcode") && !(reader.getValue().equals("NULL")))
                            adres.setPostcode(reader.getValue());
                        else if(reader.getNodeName().equals("woonplaats") && !(reader.getValue().equals("NULL")))
                            adres.setWoonplaats(reader.getValue());
                        reader.moveUp();
                    }
                }
                reader.moveUp();
            }
            
            return klant;
        }
    }
}
