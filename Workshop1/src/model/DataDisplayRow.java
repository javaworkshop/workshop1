
package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.converter.NumberStringConverter;

/**
 * Class meant to make editing and displaying a QueryResultRow in a TableView easier. Also contains
 * BooleanProperties to keep track of the state of the update and delete checkboxes.
 */
public class DataDisplayRow {
    SimpleBooleanProperty update;
    SimpleBooleanProperty delete;
    
    QueryResultRow queryResultRow;
    
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
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
        
        this.queryResultRow = queryResultRow;        
        
        /* Declareer alle properties en geef ze dezelfde waarde als de overeenkomende klant en  
        *  bestelling properties. Bind deze properties bidirectionally zodat aanpassingen in de
        *  tabel ook plaatsvinden in de klant en bestelling objecten in queryResultRow en andersom.
        */ 
        klant_id = new SimpleStringProperty(queryResultRow.getKlant().getKlant_id() + "");
        klant_id.bindBidirectional(queryResultRow.getKlant().klant_idProperty(), 
                new NumberStringConverter());
        
        huisnummer = new SimpleStringProperty(queryResultRow.getKlant().getHuisnummer() + "");
        huisnummer.bindBidirectional(queryResultRow.getKlant().huisnummerProperty(), 
                new NumberStringConverter());
        
        bestelling_id = new SimpleStringProperty(queryResultRow.getBestelling().getBestelling_id()
                + "");
        bestelling_id.bindBidirectional(queryResultRow.getBestelling().bestelling_idProperty(), 
                new NumberStringConverter());
        
        artikel_id1 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_id1() 
                + "");
        artikel_id1.bindBidirectional(queryResultRow.getBestelling().artikel_id1Property(), 
                new NumberStringConverter());
        
        artikel_id2 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_id2() 
                + "");
        artikel_id2.bindBidirectional(queryResultRow.getBestelling().artikel_id2Property(), 
                new NumberStringConverter());
        
        artikel_id3 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_id3() 
                + "");
        artikel_id3.bindBidirectional(queryResultRow.getBestelling().artikel_id3Property(), 
                new NumberStringConverter());
        
        artikel_aantal1 = new SimpleStringProperty(queryResultRow.getBestelling().
                getArtikel_aantal1() + "");
        artikel_aantal1.bindBidirectional(queryResultRow.getBestelling().artikel_aantal1Property(), 
                new NumberStringConverter());
        
        artikel_aantal2 = new SimpleStringProperty(queryResultRow.getBestelling().
                getArtikel_aantal2() + "");
        artikel_aantal2.bindBidirectional(queryResultRow.getBestelling().artikel_aantal2Property(), 
                new NumberStringConverter());
        
        artikel_aantal3 = new SimpleStringProperty(queryResultRow.getBestelling().
                getArtikel_aantal3() + "");
        artikel_aantal3.bindBidirectional(queryResultRow.getBestelling().artikel_aantal3Property(), 
                new NumberStringConverter());
        
        artikel_prijs1 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_prijs1()
                + "");
        artikel_prijs1.bindBidirectional(queryResultRow.getBestelling().artikel_prijs1Property(), 
                new NumberStringConverter());
        
        artikel_prijs2 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_prijs2()
                + "");
        artikel_prijs2.bindBidirectional(queryResultRow.getBestelling().artikel_prijs2Property(), 
                new NumberStringConverter());
        
        artikel_prijs3 = new SimpleStringProperty(queryResultRow.getBestelling().getArtikel_prijs3()
                + "");
        artikel_prijs3.bindBidirectional(queryResultRow.getBestelling().artikel_prijs3Property(), 
                new NumberStringConverter());
    }

    public QueryResultRow getQueryResultRow() {
        return queryResultRow;
    }

    public void setQueryResultRow(QueryResultRow queryResultRow) {
        this.queryResultRow = queryResultRow;
    }
    
    public StringProperty klant_idProperty() {
        return klant_id;
    }
    public StringProperty voornaamProperty() {
        return queryResultRow.getKlant().voornaamProperty();
    }
    public StringProperty tussenvoegselProperty() {
        return queryResultRow.getKlant().tussenvoegselProperty();
    }
    public StringProperty achternaamProperty() {
        return queryResultRow.getKlant().achternaamProperty();
    }
    public StringProperty emailProperty() {
        return queryResultRow.getKlant().emailProperty();
    }
    
    public StringProperty straatnaamProperty() {
        return queryResultRow.getKlant().straatnaamProperty();
    }
    
    public StringProperty huisnummerProperty() {
        return huisnummer;
    }
    
    public StringProperty toevoegingProperty() {
        return queryResultRow.getKlant().toevoegingProperty();
    }
    
    public StringProperty postcodeProperty() {
        return queryResultRow.getKlant().postcodeProperty();
    }
    
    public StringProperty woonplaatsProperty() {
        return queryResultRow.getKlant().woonplaatsProperty();
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
        return queryResultRow.getBestelling().artikel_naam1Property();
    }
    
    public StringProperty artikel_naam2Property() {
        return queryResultRow.getBestelling().artikel_naam2Property();
    }
    
    public StringProperty artikel_naam3Property() {
        return queryResultRow.getBestelling().artikel_naam3Property();
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
    
    public void setValue(boolean bool) {
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
}
