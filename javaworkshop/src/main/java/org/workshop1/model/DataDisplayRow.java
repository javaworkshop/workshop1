package org.workshop1.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class meant to make editing and displaying data in a TableView easier. Also contains
 * BooleanProperties to keep track of the state of the update and delete checkboxes.
 */
public class DataDisplayRow {
    // klant properties
    private SimpleStringProperty klant_id;
    private SimpleStringProperty voornaam;
    private SimpleStringProperty tussenvoegsel;
    private SimpleStringProperty achternaam;
    private SimpleStringProperty email;
    private SimpleStringProperty huisnummer;
    private SimpleStringProperty postcode;
    private SimpleStringProperty straatnaam;
    private SimpleStringProperty toevoeging;
    private SimpleStringProperty woonplaats;    
    private Klant klant;
    
    // bestelling properties
    private SimpleStringProperty bestelling_id;
    private SimpleStringProperty artikel_id1;
    private SimpleStringProperty artikel_id2;
    private SimpleStringProperty artikel_id3;
    private SimpleStringProperty artikel_naam1;
    private SimpleStringProperty artikel_naam2;
    private SimpleStringProperty artikel_naam3;
    private SimpleStringProperty artikel_aantal1;
    private SimpleStringProperty artikel_aantal2;    
    private SimpleStringProperty artikel_aantal3;
    private SimpleStringProperty artikel_prijs1;
    private SimpleStringProperty artikel_prijs2;
    private SimpleStringProperty artikel_prijs3;
    private Bestelling bestelling;    
    
    // checkbox properties
    private SimpleBooleanProperty update;
    private SimpleBooleanProperty delete;

    
    
    public DataDisplayRow(QueryResultRow queryResultRow) {
        klant = queryResultRow.getKlant();       
        bestelling = queryResultRow.getBestelling();
        
        initBooleanProperties();
        initKlantProperties();
        initBestellingProperties();
    }
    
    public DataDisplayRow(Klant klant, Bestelling bestelling) {
        this.klant = klant;
        this.bestelling = bestelling;
        
        initBooleanProperties();
        initKlantProperties();
        initBestellingProperties();
    }

    public StringProperty achternaamProperty() {
        return achternaam;
    }

    public StringProperty artikel_aantal1Property() {
        return artikel_aantal1;
    }

    public StringProperty artikel_aantal2Property() {
        return artikel_aantal2;
    }

    public StringProperty artikel_aantal3Property() {
        return artikel_aantal3;
    }
    
    public StringProperty artikel_id1Property() {
        return artikel_id1;
    }
    
    public StringProperty artikel_id2Property() {
        return artikel_id2;
    }
    
    public StringProperty artikel_id3Property() {
        return artikel_id3;
    }
    
    public StringProperty artikel_naam1Property() {
        return artikel_naam1Property();
    }
    
    public StringProperty artikel_naam2Property() {
        return artikel_naam2Property();
    }
    
    public StringProperty artikel_naam3Property() {
        return artikel_naam3Property();
    }
    
    public StringProperty artikel_prijs1Property() {
        return artikel_prijs1;
    }
    
    public StringProperty artikel_prijs2Property() {
        return artikel_prijs2;
    }
    
    public StringProperty artikel_prijs3Property() {
        return artikel_prijs3;
    }
    
    public StringProperty bestelling_idProperty() {
        return bestelling_id;
    }
    
    public BooleanProperty deleteProperty() {
        return delete;
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        DataDisplayRow ddr = (DataDisplayRow)obj;
        return  ddr.klant.equals(this.klant) &&
                ddr.bestelling.equals(this.bestelling) &&
                ddr.update.equals(this.update) &&
                ddr.delete.equals(this.delete);
    }
    
    public Bestelling getBestelling() {
        return bestelling;
    }
    
    public boolean getDelete() {
        return delete.getValue();
    }
    
    public Klant getKlant() {
        return klant;
    }
    
    public boolean getUpdate() {
        return update.getValue();
    }
    
    @Override
    public int hashCode() {
        int hash = 29;
        int multiplier = 17;
        
        return  multiplier * hash +
                klant.hashCode() +
                bestelling.hashCode() +
                update.getValue().hashCode() +
                delete.getValue().hashCode();
    }
    
    public StringProperty huisnummerProperty() {
        return huisnummer;
    }
    
    /**
     * Declare bestelling properties and bind them bidirectionally to the corresponding properties
     * in bestelling so changes made to the TableView will be reflected in bestelling and vice
     * versa.
     */
    private void initBestellingProperties() {
        bestelling_id = new SimpleStringProperty(bestelling.getBestelling_id() + "");
        
        artikel_id1 = new SimpleStringProperty(bestelling.getArtikel_id1() + "");
        artikel_id1.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_id1(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_id1.setValue(o);
            }
        });
        
        artikel_id2 = new SimpleStringProperty(bestelling.getArtikel_id2() + "");
        artikel_id2.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_id2(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_id2.setValue(o);
            }
        });
        
        artikel_id3 = new SimpleStringProperty(bestelling.getArtikel_id3() + "");
        artikel_id3.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_id3(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_id3.setValue(o);
            }
        });
        
        artikel_naam1 = new SimpleStringProperty(bestelling.getArtikel_naam1());
        artikel_naam1.addListener((ov, o, n) -> {
            bestelling.setArtikel_naam1(n);
        });
        
        artikel_naam2 = new SimpleStringProperty(bestelling.getArtikel_naam2());
        artikel_naam2.addListener((ov, o, n) -> {
            bestelling.setArtikel_naam2(n);
        });
        
        artikel_naam3 = new SimpleStringProperty(bestelling.getArtikel_naam3());
        artikel_naam3.addListener((ov, o, n) -> {
            bestelling.setArtikel_naam3(n);
        });
        
        artikel_aantal1 = new SimpleStringProperty(bestelling.getArtikel_aantal1() + "");
        artikel_aantal1.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_aantal1(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_aantal1.setValue(o);
            }
        });
        
        artikel_aantal2 = new SimpleStringProperty(bestelling.getArtikel_aantal2() + "");
        artikel_aantal2.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_aantal2(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_aantal2.setValue(o);
            }
        });
        
        artikel_aantal3 = new SimpleStringProperty(bestelling.getArtikel_aantal3() + "");
        artikel_aantal3.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_aantal3(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                artikel_aantal3.setValue(o);
            }
        });
        
        artikel_prijs1 = new SimpleStringProperty(bestelling.getArtikel_prijs1() + "");
        artikel_prijs1.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_prijs1(Double.parseDouble(n));
            }
            catch(NumberFormatException ex) {
                artikel_prijs1.setValue(o);
            }
        });
        
        artikel_prijs2 = new SimpleStringProperty(bestelling.getArtikel_prijs2() + "");
        artikel_prijs2.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_prijs2(Double.parseDouble(n));
            }
            catch(NumberFormatException ex) {
                artikel_prijs2.setValue(o);
            }
        });
        
        artikel_prijs3 = new SimpleStringProperty(bestelling.getArtikel_prijs3() + "");
        artikel_prijs3.addListener((ov, o, n) -> {
            try {
                bestelling.setArtikel_prijs3(Double.parseDouble(n));
            }
            catch(NumberFormatException ex) {
                artikel_prijs3.setValue(o);
            }
        });
    }

    /**
     * Declare boolean properties that correspond to the state of the update and delete checkboxes
     * in the TableView.
     */
    private void initBooleanProperties() {
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
    }
    
    /**
     * Declare klant properties and bind them to the corresponding properties in
     * klant so changes made to the TableView will be reflected in klant.
     */
    private void initKlantProperties() {
        klant_id = new SimpleStringProperty(klant.getKlant_id() + "");
        
        voornaam = new SimpleStringProperty(klant.getVoornaam() + "");
        email.addListener((ov, o, n) -> {
            klant.setVoornaam(n);
        });
        
        tussenvoegsel = new SimpleStringProperty(klant.getTussenvoegsel());
        tussenvoegsel.addListener((ov, o, n) -> {
            klant.setTussenvoegsel(n);
        });
        
        achternaam = new SimpleStringProperty(klant.getAchternaam() + "");
        email.addListener((ov, o, n) -> {
            klant.setAchternaam(n);
        });
        
        email = new SimpleStringProperty(klant.getEmail());
        email.addListener((ov, o, n) -> {
            klant.setEmail(n);
        });
        
        huisnummer = new SimpleStringProperty(klant.getAdres().getHuisnummer() + "");
        huisnummer.addListener((ov, o, n) -> {
            try {
                klant.getAdres().setHuisnummer(Integer.parseInt(n));
            }
            catch(NumberFormatException ex) {
                huisnummer.setValue(o);
            }
        });
        
        postcode = new SimpleStringProperty(klant.getAdres().getPostcode());
        postcode.addListener((ov, o, n) -> {
            klant.getAdres().setPostcode(n);
        });
        
        straatnaam = new SimpleStringProperty(klant.getAdres().getStraatnaam());
        straatnaam.addListener((ov, o, n) -> {
            klant.getAdres().setStraatnaam(n);
        });
        
        toevoeging = new SimpleStringProperty(klant.getAdres().getToevoeging());
        toevoeging.addListener((ov, o, n) -> {
            klant.getAdres().setToevoeging(n);
        });
        
        woonplaats = new SimpleStringProperty(klant.getAdres().getWoonplaats());
        woonplaats.addListener((ov, o, n) -> {
            klant.getAdres().setWoonplaats(n);
        });
    }
    
    public StringProperty klant_idProperty() {
        return klant_id;
    }
    
    /**
     * Bind the boolean properties, associated with the update and delete checkboxes, together, such
     * that only one of them can be ticked at the same time.
     */
    public void makeExclusiveCheckBoxes() {
        update.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, 
                    Boolean newValue) {
                if(newValue == true && delete.getValue() == true)
                    delete.setValue(false);
            }
        });
        
        delete.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, 
                    Boolean newValue) {
                if(newValue == true && update.getValue() == true)
                    update.setValue(false);
            }
        });
    }
    
    public StringProperty postcodeProperty() {
        return postcode;
    }
    
    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
        initBestellingProperties();
    }

    public void setDelete(boolean bool) {
        delete.setValue(bool);
    }
    
    public void setKlant(Klant klant) {
        this.klant = klant;
        initKlantProperties();
    }
    
    public void setUpdate(boolean bool) {
        update.setValue(bool);
    }
    
    public StringProperty straatnaamProperty() {
        return straatnaam;
    }
    
    @Override
    public String toString() {
        return "DataDisplayRow{" + "update=" + update + ", delete=" + delete + 
                ", klant=" + klant + ", bestelling=" + bestelling + '}';
    }
    
    public StringProperty toevoegingProperty() {
        return toevoeging;
    }
    
    public StringProperty tussenvoegselProperty() {
        return tussenvoegsel;
    }
    
    public BooleanProperty updateProperty() {
        return update;
    }
    
    public StringProperty voornaamProperty() {
        return voornaam;
    }
    
    public StringProperty woonplaatsProperty() {
        return woonplaats;
    }
}
