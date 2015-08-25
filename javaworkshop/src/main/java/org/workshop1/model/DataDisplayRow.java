package org.workshop1.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.converter.NumberStringConverter;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

/**
 * Class meant to make editing and displaying data in a TableView easier. Also contains
 * BooleanProperties to keep track of the state of the update and delete checkboxes.
 */
public class DataDisplayRow {
    SimpleBooleanProperty update;
    SimpleBooleanProperty delete;
    
    Klant klant;
    Bestelling bestelling;
    
    // klant properties
    private SimpleStringProperty klant_id;          // Integer and double properties are converted 
    private SimpleStringProperty huisnummer;        // to String properties to support easy display
                                                    // and editing in a TableView.
    // bestelling properties
    private SimpleStringProperty bestelling_id;
    private SimpleStringProperty artikel_id1;
    private SimpleStringProperty artikel_id2;
    private SimpleStringProperty artikel_id3;
    private SimpleStringProperty artikel_aantal1;
    private SimpleStringProperty artikel_aantal2;
    private SimpleStringProperty artikel_aantal3;
    private SimpleStringProperty artikel_prijs1;
    private SimpleStringProperty artikel_prijs2;
    private SimpleStringProperty artikel_prijs3;
    
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
    
    /**
    * Declare boolean properties that correspond to the state of the update and delete checkboxes
    * in the TableView.
    */ 
    private void initBooleanProperties() {
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
    }
    
    /**
     * Declare klant properties and bind them bidirectionally to the corresponding properties in
     * klant so changes made to the TableView will be reflected in klant and vice versa.
     */
    private void initKlantProperties() {
        klant_id = new SimpleStringProperty(klant.getKlant_id() + "");
        klant_id.bindBidirectional(klant.klant_idProperty(), 
                new NumberStringConverter());
        
        huisnummer = new SimpleStringProperty(klant.getHuisnummer() + "");
        huisnummer.bindBidirectional(klant.huisnummerProperty(),
                new NumberStringConverter());
    }
    
    /**
     * Declare bestelling properties and bind them bidirectionally to the corresponding properties 
     * in bestelling so changes made to the TableView will be reflected in bestelling and vice 
     * versa.
     */
    private void initBestellingProperties() {
        bestelling_id = new SimpleStringProperty(bestelling.getBestelling_id() + "");
        bestelling_id.bindBidirectional(bestelling.bestelling_idProperty(),
                new NumberStringConverter());
        
        artikel_id1 = new SimpleStringProperty(bestelling.getArtikel_id1() + "");
        artikel_id1.bindBidirectional(bestelling.artikel_id1Property(), 
                new NumberStringConverter());
        
        artikel_id2 = new SimpleStringProperty(bestelling.getArtikel_id2() + "");
        artikel_id2.bindBidirectional(bestelling.artikel_id2Property(), 
                new NumberStringConverter());
        
        artikel_id3 = new SimpleStringProperty(bestelling.getArtikel_id3() + "");
        artikel_id3.bindBidirectional(bestelling.artikel_id3Property(), 
                new NumberStringConverter());
        
        artikel_aantal1 = new SimpleStringProperty(bestelling.getArtikel_aantal1() + "");
        artikel_aantal1.bindBidirectional(bestelling.artikel_aantal1Property(), 
                new NumberStringConverter());
        
        artikel_aantal2 = new SimpleStringProperty(bestelling.getArtikel_aantal2() + "");
        artikel_aantal2.bindBidirectional(bestelling.artikel_aantal2Property(), 
                new NumberStringConverter());
        
        artikel_aantal3 = new SimpleStringProperty(bestelling.getArtikel_aantal3() + "");
        artikel_aantal3.bindBidirectional(bestelling.artikel_aantal3Property(), 
                new NumberStringConverter());
        
        artikel_prijs1 = new SimpleStringProperty(bestelling.getArtikel_prijs1() + "");
        artikel_prijs1.bindBidirectional(bestelling.artikel_prijs1Property(), 
                new NumberStringConverter());
        
        artikel_prijs2 = new SimpleStringProperty(bestelling.getArtikel_prijs2() + "");
        artikel_prijs2.bindBidirectional(bestelling.artikel_prijs2Property(), 
                new NumberStringConverter());
        
        artikel_prijs3 = new SimpleStringProperty(bestelling.getArtikel_prijs3() + "");
        artikel_prijs3.bindBidirectional(bestelling.artikel_prijs3Property(), 
                new NumberStringConverter());
    }

    public Klant getKlant() {
        return klant;
    }
    
    public Bestelling getBestelling() {
        return bestelling;
    }
    
    public void setKlant(Klant klant) {
        this.klant = klant;
        initKlantProperties();
    }
    
    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
        initBestellingProperties();
    }
    
    public StringProperty klant_idProperty() {
        return klant_id;
    }
    
    public StringProperty voornaamProperty() {
        return klant.voornaamProperty();
    }
    
    public StringProperty tussenvoegselProperty() {
        return klant.tussenvoegselProperty();
    }
    
    public StringProperty achternaamProperty() {
        return klant.achternaamProperty();
    }
    
    public StringProperty emailProperty() {
        return klant.emailProperty();
    }
    
    public StringProperty straatnaamProperty() {
        return klant.straatnaamProperty();
    }
    
    public StringProperty huisnummerProperty() {
        return huisnummer;
    }
    
    public StringProperty toevoegingProperty() {
        return klant.toevoegingProperty();
    }
    
    public StringProperty postcodeProperty() {
        return klant.postcodeProperty();
    }
    
    public StringProperty woonplaatsProperty() {
        return klant.woonplaatsProperty();
    }
    
    public StringProperty bestelling_idProperty() {
        return bestelling_id;
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
        return bestelling.artikel_naam1Property();
    }
    
    public StringProperty artikel_naam2Property() {
        return bestelling.artikel_naam2Property();
    }
    
    public StringProperty artikel_naam3Property() {
        return bestelling.artikel_naam3Property();
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

    public StringProperty artikel_prijs1Property() {
        return artikel_prijs1;
    }
    
    public StringProperty artikel_prijs2Property() {
        return artikel_prijs2;
    }
    
    public StringProperty artikel_prijs3Property() {
        return artikel_prijs3;
    }

    public boolean getUpdate() {
        return update.getValue();
    }
    
    public void setUpdate(boolean bool) {
        update.setValue(bool);
    }
    
    public BooleanProperty updateProperty() {
        return update;
    }
    
    public boolean getDelete() {
        return delete.getValue();
    }
    
    public void setDelete(boolean bool) {
        delete.setValue(bool);
    }
    
    public BooleanProperty deleteProperty() {
        return delete;
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

    @Override
    public String toString() {
        return "DataDisplayRow{" + "update=" + update + ", delete=" + delete + 
                ", klant=" + klant + ", bestelling=" + bestelling + '}';
    }
}
