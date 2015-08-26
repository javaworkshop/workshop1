package org.workshop1.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class to store data from the klant table.
 */
public class Klant extends Data {
    private final SimpleStringProperty achternaam;
    private final SimpleStringProperty email;
    private final SimpleIntegerProperty huisnummer;
    private final SimpleStringProperty postcode;
    private final SimpleStringProperty straatnaam;
    private final SimpleStringProperty toevoeging;
    private final SimpleStringProperty tussenvoegsel;
    private final SimpleStringProperty voornaam;
    private final SimpleStringProperty woonplaats;

    public Klant() {
        voornaam = new SimpleStringProperty(null);
        tussenvoegsel = new SimpleStringProperty(null);
        achternaam = new SimpleStringProperty(null);
        email = new SimpleStringProperty(null);
        straatnaam = new SimpleStringProperty(null);
        huisnummer = new SimpleIntegerProperty(0);
        toevoeging = new SimpleStringProperty(null);
        postcode = new SimpleStringProperty(null);
        woonplaats= new SimpleStringProperty(null);
    }
    
    public StringProperty achternaamProperty() {
        return achternaam;
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
        
        Klant k = (Klant)obj;
        return  k.getKlant_id() == this.getKlant_id() &&
                k.getHuisnummer() == this.getHuisnummer() &&
                (k.getVoornaam() == null && this.getVoornaam() == null ||
                k.getVoornaam().equals(this.getVoornaam())) &&
                (k.getTussenvoegsel() == null && this.getTussenvoegsel() == null ||
                k.getTussenvoegsel().equals(this.getTussenvoegsel())) &&
                (k.getAchternaam() == null && this.getAchternaam() == null ||
                k.getAchternaam().equals(this.getAchternaam())) &&
                (k.getEmail() == null && this.getEmail() == null ||
                k.getEmail().equals(this.getEmail())) &&
                (k.getStraatnaam() == null && this.getStraatnaam() == null ||
                k.getStraatnaam().equals(this.getStraatnaam())) &&
                (k.getToevoeging() == null && this.getToevoeging() == null ||
                k.getToevoeging().equals(this.getToevoeging())) &&
                (k.getPostcode() == null && this.getPostcode() == null ||
                k.getPostcode().equals(this.getPostcode())) &&
                (k.getWoonplaats() == null && this.getWoonplaats() == null ||
                k.getWoonplaats().equals(this.getWoonplaats()));
    }
    
    public String getAchternaam() {
        return achternaam.getValue();
    }
    
    public String getEmail() {
        return email.getValue();
    }
    
    public int getHuisnummer() {
        return huisnummer.getValue();
    }
    
    public int getKlant_id() {
        return super.getPrimaryKey();
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

    public String getTussenvoegsel() {
        return tussenvoegsel.getValue();
    }
    
    public String getVoornaam() {
        return voornaam.getValue();
    }

    public String getWoonplaats() {
        return woonplaats.getValue();
    }

    @Override
    public int hashCode() {
        int hash = 11;
        int multiplier = 5;
        
        return  multiplier * hash +
                getKlant_id() +
                getVoornaam().hashCode() +
                getTussenvoegsel().hashCode() +
                getAchternaam().hashCode() +
                getEmail().hashCode() +
                getStraatnaam().hashCode() +
                getToevoeging().hashCode() +
                getPostcode().hashCode() +
                getWoonplaats().hashCode();
    }
    
    public IntegerProperty huisnummerProperty() {
        return huisnummer;
    }
    
    public IntegerProperty klant_idProperty() {
        return super.primaryKeyProperty();
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }
    
    public void setAchternaam(String achternaam) {
        this.achternaam.setValue(achternaam);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setHuisnummer(int huisnummer) {
        this.huisnummer.setValue(huisnummer);
    }
    
    public void setKlant_id(int id) {
        super.setPrimaryKey(id);
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

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel.setValue(tussenvoegsel);
    }

    public void setVoornaam(String voornaam) {
        this.voornaam.setValue(voornaam);
    }
    
    public void setWoonplaats(String woonplaats) {
        this.woonplaats.setValue(woonplaats);
    }

    public StringProperty straatnaamProperty() {
        return straatnaam;
    }

    @Override
    public String toString() {
        return "Klant{" + "klant_id=" + super.primaryKeyProperty() + ", voornaam=" + voornaam +
                ", tussenvoegsel=" + tussenvoegsel + ", achternaam=" + achternaam +
                ", email=" + email + ", straatnaam=" + straatnaam + ", huisnummer=" + huisnummer +
                ", toevoeging=" + toevoeging + ", postcode=" + postcode +
                ", woonplaats=" + woonplaats + '}';
    }
    
    public StringProperty toevoegingProperty() {
        return toevoeging;
    }
    
    public StringProperty tussenvoegselProperty() {
        return tussenvoegsel;
    }
    
    public StringProperty voornaamProperty() {
        return voornaam;
    } 

    public StringProperty woonplaatsProperty() {
        return woonplaats;
    }
}
