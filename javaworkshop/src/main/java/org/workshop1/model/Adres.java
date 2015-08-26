package org.workshop1.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Adres {
    private final SimpleIntegerProperty huisnummer;
    private final SimpleStringProperty postcode;
    private final SimpleStringProperty straatnaam;
    private final SimpleStringProperty toevoeging;
    private final SimpleStringProperty woonplaats;
    
    public Adres() {
        straatnaam = new SimpleStringProperty(null);
        huisnummer = new SimpleIntegerProperty(0);
        toevoeging = new SimpleStringProperty(null);
        postcode = new SimpleStringProperty(null);
        woonplaats= new SimpleStringProperty(null);
    }
    
    public void setHuisnummer(int huisnummer) {
        this.huisnummer.setValue(huisnummer);
    }
    
    public void setPostcode(String postcode) {
        this.postcode.setValue(postcode);
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam.setValue(straatnaam);
    }
    
    public void setToevoeging(String toevoeging) {
        this.toevoeging.setValue(toevoeging);
    }
   
    public void setWoonplaats(String woonplaats) {
        this.woonplaats.setValue(woonplaats);
    }
    
    public int getHuisnummer() {
        return huisnummer.getValue();
    }
    
    public String getPostcode() {
        return postcode.getValue();
    }
    
    public String getStraatnaam() {
        return straatnaam.getValue();
    }

    public String getToevoeging() {
        return toevoeging.getValue();
    }

    public String getWoonplaats() {
        return woonplaats.getValue();
    }

    public IntegerProperty HuisnummerProperty() {
        return huisnummer;
    }

    public StringProperty PostcodeProperty() {
        return postcode;
    }

    public StringProperty StraatnaamProperty() {
        return straatnaam;
    }

    public StringProperty ToevoegingProperty() {
        return toevoeging;
    }

    public StringProperty WoonplaatsProperty() {
        return woonplaats;
    }
}
