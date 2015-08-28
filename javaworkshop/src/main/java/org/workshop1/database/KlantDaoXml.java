package org.workshop1.database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.beans.XMLEncoder;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.workshop1.model.Klant;


public class KlantDaoXml /*implements KlantDao*/ {
    public static final String DEFAULT_LOCATION = "data/xml";
    private File klantFile;
    private XMLEncoder encoder;
    XStream xStream;
    
    public KlantDaoXml() throws DaoConfigurationException {
        this(DEFAULT_LOCATION);
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
        //xStream.alias("adres", Adres.class);
        xStream.registerConverter(new KlantConverter(), XStream.PRIORITY_VERY_HIGH);
    }
/*    
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
*/  
    // werkt niet (o.a. omdat unmarshal methode onderaan nog niet is geimplementeerd
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
        try(ObjectOutputStream klantOutputStream = xStream.createObjectOutputStream(
                new PrettyPrintWriter(new FileWriter(klantFile)), "klanten")) {
            klantOutputStream.writeObject(klant);
            // overschrijft bij iedere nieuwe klant het hele bestand
        }
        catch(IOException ex) {
            throw new DaoException("Schrijven naar " + klantFile + " is mislukt", ex);
        }
    }
/*    
    public void update(Klant klant) throws DaoException {
        add(klant);
    }
    
    public void delete(Klant klant) throws DaoException {
        
    }*/
    
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
   
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            reader.moveDown();
            reader.moveUp();
            return new Klant();
        }
    }
}
