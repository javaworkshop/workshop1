package org.workshop1.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to store data from the klant table.
 */
@Entity
@Table(name = "klanten")
public class Klant implements Data, Comparable<Klant> {
    @Id
    @Column(name="klant_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int klant_id;
    @Column(name = "voornaam", length = 50)
    private String voornaam;
    @Column(name = "tussenvoegsel", length = 20)
    private String tussenvoegsel;
    @Column(name = "achternaam", length = 51)
    private String achternaam;
    @Column(name = "email", length = 320)
    private String email;
    @Embedded // Later @ManyToMany
    private Adres adres;    

    public Klant() {
        klant_id = 0;
        adres = new Adres();
        voornaam = null;
        tussenvoegsel = null;
        achternaam = null;
        email = null;
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
        return achternaam;
    }
    
    public String getEmail() {
        return email;
    }
    
    public int getHuisnummer() {
        return adres.getHuisnummer();
    }    
    
    public int getKlant_id() {
        return klant_id;
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
        return tussenvoegsel;
    }    
    
    public String getVoornaam() {
        return voornaam;
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
    
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHuisnummer(int huisnummer) {
        adres.setHuisnummer(huisnummer);
    }
    
    public void setKlant_id(int id) {
        klant_id = id;
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
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    
    public void setWoonplaats(String woonplaats) {
        adres.setWoonplaats(woonplaats);
    }
    
    @Override
    public String toString() {
        return "Klant{" + "klant_id=" + klant_id + ", voornaam=" + voornaam +
                ", tussenvoegsel=" + tussenvoegsel + ", achternaam=" + achternaam +
                ", email=" + email + ", straatnaam=" + adres.getStraatnaam() + ", huisnummer=" + 
                adres.getHuisnummer() + ", toevoeging=" + adres.getToevoeging() + ", postcode=" + 
                adres.getPostcode() + ", woonplaats=" + adres.getWoonplaats() + '}';
    }
    
    public Adres getAdres() {
        return adres;
    }
    
    public void setAdres(Adres adres) {
        this.adres = adres;
    }
}
