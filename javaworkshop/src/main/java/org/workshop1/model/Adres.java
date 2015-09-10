package org.workshop1.model;

import javax.persistence.Embeddable;
import java.util.Objects;
import javax.persistence.Column;

@Embeddable // krijgt als het goed is eigen tabel...
public class Adres implements Data {
    @Column
    private int huisnummer;
    @Column
    private String postcode;
    @Column
    private String straatnaam;
    @Column
    private String toevoeging;
    @Column
    private String woonplaats;
    
    public Adres() {
        straatnaam = null;
        huisnummer = 0;
        toevoeging = null;
        postcode = null;
        woonplaats= null;
    }
    
    public void setHuisnummer(int huisnummer) {
        this.huisnummer = huisnummer;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }
    
    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }
   
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    
    public int getHuisnummer() {
        return huisnummer;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public String getStraatnaam() {
        return straatnaam;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public String getWoonplaats() {
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
