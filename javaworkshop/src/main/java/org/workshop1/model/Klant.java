package org.workshop1.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class to store data from the klant table.
 */
public class Klant extends Data implements Comparable<Klant> {
    private Adres adres;
    private final SimpleStringProperty achternaam;
    private final SimpleStringProperty email;
    private final SimpleStringProperty tussenvoegsel;
    private final SimpleStringProperty voornaam;

    public Klant() {
        adres = new Adres();
        voornaam = new SimpleStringProperty(null);
        tussenvoegsel = new SimpleStringProperty(null);
        achternaam = new SimpleStringProperty(null);
        email = new SimpleStringProperty(null);
    }
    
    public StringProperty achternaamProperty() {
        return achternaam;
    }

    @Override
    public int compareTo(Klant k) {
        if(k.getKlant_id() < this.getKlant_id())
            return 1;
        else if(k.getKlant_id() > this.getKlant_id())
            return -1;
        else
            return 0;
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
                (k.getVoornaam() == null && this.getVoornaam() == null ||
                k.getVoornaam().equals(this.getVoornaam())) &&
                (k.getTussenvoegsel() == null && this.getTussenvoegsel() == null ||
                k.getTussenvoegsel().equals(this.getTussenvoegsel())) &&
                (k.getAchternaam() == null && this.getAchternaam() == null ||
                k.getAchternaam().equals(this.getAchternaam())) &&
                (k.getEmail() == null && this.getEmail() == null ||
                k.getEmail().equals(this.getEmail())) &&
                k.getAdres().equals(this.getAdres());
    }
    
    public String getAchternaam() {
        return achternaam.getValue();
    }
    
    public String getEmail() {
        return email.getValue();
    }
    
    public int getHuisnummer() {
        return adres.getHuisnummer();
    }
    
    public int getKlant_id() {
        return super.getPrimaryKey();
    }
    
    public String getPostcode() {
        return adres.getPostcode();
    }
    
    public String getStraatnaam() {
        return adres.getStraatnaam();
    }

    public String getToevoeging() {
        return adres.getToevoeging();
    }

    public String getTussenvoegsel() {
        return tussenvoegsel.getValue();
    }
    
    public String getVoornaam() {
        return voornaam.getValue();
    }

    public String getWoonplaats() {
        return adres.getWoonplaats();
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
        return adres.huisnummerProperty();
    }
    
    public IntegerProperty klant_idProperty() {
        return super.primaryKeyProperty();
    }

    public StringProperty postcodeProperty() {
        return adres.postcodeProperty();
    }
    
    public void setAchternaam(String achternaam) {
        this.achternaam.setValue(achternaam);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setHuisnummer(int huisnummer) {
        adres.setHuisnummer(huisnummer);
    }
    
    public void setKlant_id(int id) {
        super.setPrimaryKey(id);
    }

    public void setPostcode(String postcode) {
        adres.setPostcode(postcode);
    }

    public void setStraatnaam(String straatnaam) {
        adres.setStraatnaam(straatnaam);
    }
    
    public void setToevoeging(String toevoeging) {
        adres.setToevoeging(toevoeging);
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel.setValue(tussenvoegsel);
    }

    public void setVoornaam(String voornaam) {
        this.voornaam.setValue(voornaam);
    }
    
    public void setWoonplaats(String woonplaats) {
        adres.setWoonplaats(woonplaats);
    }

    public StringProperty straatnaamProperty() {
        return adres.straatnaamProperty();
    }

    @Override
    public String toString() {
        return "Klant{" + "klant_id=" + super.primaryKeyProperty() + ", voornaam=" + voornaam +
                ", tussenvoegsel=" + tussenvoegsel + ", achternaam=" + achternaam +
                ", email=" + email + ", straatnaam=" + adres.getStraatnaam() + ", huisnummer=" + 
                adres.getHuisnummer() + ", toevoeging=" + adres.getToevoeging() + ", postcode=" + 
                adres.getPostcode() + ", woonplaats=" + adres.getWoonplaats() + '}';
    }
    
    public StringProperty toevoegingProperty() {
        return adres.toevoegingProperty();
    }
    
    public StringProperty tussenvoegselProperty() {
        return tussenvoegsel;
    }
    
    public StringProperty voornaamProperty() {
        return voornaam;
    }

    public StringProperty woonplaatsProperty() {
        return adres.woonplaatsProperty();
    }
    
    public Adres getAdres() {
        return adres;
    }
    
    public void setAdres(Adres adres) {
        this.adres = adres;
    }
}
