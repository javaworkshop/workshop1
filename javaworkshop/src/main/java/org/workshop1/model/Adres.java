package org.workshop1.model;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Adres /*extends Data*/ {
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

    public IntegerProperty huisnummerProperty() {
        return huisnummer;
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public StringProperty straatnaamProperty() {
        return straatnaam;
    }

    public StringProperty toevoegingProperty() {
        return toevoeging;
    }

    public StringProperty woonplaatsProperty() {
        return woonplaats;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        Adres a = (Adres)obj;
        return  a.getHuisnummer() == this.getHuisnummer() &&                
                (a.getStraatnaam() == null && this.getStraatnaam() == null ||
                a.getStraatnaam().equals(this.getStraatnaam())) &&
                (a.getToevoeging() == null && this.getToevoeging() == null ||
                a.getToevoeging().equals(this.getToevoeging())) &&
                (a.getPostcode() == null && this.getPostcode() == null ||
                a.getPostcode().equals(this.getPostcode())) &&
                (a.getWoonplaats() == null && this.getWoonplaats() == null ||
                a.getWoonplaats().equals(this.getWoonplaats()));
    }

    @Override
    public String toString() {
        return "Adres{" + "huisnummer=" + huisnummer + ", postcode=" + postcode + ", straatnaam=" 
                + straatnaam + ", toevoeging=" + toevoeging + ", woonplaats=" + woonplaats + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.huisnummer);
        hash = 89 * hash + Objects.hashCode(this.postcode);
        hash = 89 * hash + Objects.hashCode(this.straatnaam);
        hash = 89 * hash + Objects.hashCode(this.toevoeging);
        hash = 89 * hash + Objects.hashCode(this.woonplaats);
        return hash;
    }
    
    
}
