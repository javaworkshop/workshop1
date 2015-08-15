package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class used by queryResult to represent rows. All properties are converted to String properties to
 * support easy display and editing in a TableView. (bestelling properties nog niet!)
 */
public class QueryResultRow {
    private Klant klant;
    private Bestelling bestelling;
    
    
    public QueryResultRow() {
        klant = new Klant();
        bestelling = new Bestelling();
    }
    
    public Klant getKlant() {
        return klant;
    }
    
    public void setKlant(Klant k) {
        klant = k;
    }
    
    public Bestelling getBestelling() {
        return bestelling;
    }
    
    public void setBestelling(Bestelling b) {
        bestelling = b;
    }
    
    // todo...
    @Override
    public int hashCode() {
        return 0;
    }
    
    // todo...
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}