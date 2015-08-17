package model;

//import java.util.ArrayList;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class to store data from the klant table.
 */
public class Klant extends Data {
    private final SimpleStringProperty voornaam;
    private final SimpleStringProperty tussenvoegsel;
    private final SimpleStringProperty achternaam;
    private final SimpleStringProperty email;
    private final SimpleStringProperty straatnaam;
    private final SimpleIntegerProperty huisnummer;
    private final SimpleStringProperty toevoeging;
    private final SimpleStringProperty postcode;
    private final SimpleStringProperty woonplaats;
    //private ArrayList<Bestelling> bestellingen; misschien moet deze er nog bij...

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
    
    public int getKlant_id() {
        return super.getPrimaryKey();
    }
    
    public void setKlant_id(int id) {
        super.setPrimaryKey(id);
    }
    
    public IntegerProperty klant_idProperty() {
        return super.primaryKeyProperty();
    }
    
    public String getVoornaam() {
        return voornaam.getValue();
    }
    
    public void setVoornaam(String voornaam) {
        this.voornaam.setValue(voornaam);
    }
    
    public StringProperty voornaamProperty() {
        return voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel.getValue();
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel.setValue(tussenvoegsel);
    }
    
    public StringProperty tussenvoegselProperty() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam.getValue();
    }

    public void setAchternaam(String achternaam) {
        this.achternaam.setValue(achternaam);
    }
    
    public StringProperty achternaamProperty() {
        return achternaam;
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }
    
    public StringProperty emailProperty() {
        return email;
    }

    public String getStraatnaam() {
        return straatnaam.getValue();
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam.setValue(straatnaam);
    }
    
    public StringProperty straatnaamProperty() {
        return straatnaam;
    }

    public int getHuisnummer() {
        return huisnummer.getValue();
    }

    public void setHuisnummer(int huisnummer) {
        this.huisnummer.setValue(huisnummer);
    }
    
    public IntegerProperty huisnummerProperty() {
        return huisnummer;
    }

    public String getToevoeging() {
        return toevoeging.getValue();
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging.setValue(toevoeging);
    }
    
    public StringProperty toevoegingProperty() {
        return toevoeging;
    }

    public String getPostcode() {
        return postcode.getValue();
    }

    public void setPostcode(String postcode) {
        this.postcode.setValue(postcode);
    }
    
    public StringProperty postcodeProperty() {
        return postcode;
    }

    public String getWoonplaats() {
        return woonplaats.getValue();
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats.setValue(woonplaats);
    }
    
    public StringProperty woonplaatsProperty() {
        return woonplaats;
    }
    
    // todo... gewoon primaryKey returnen?
    @Override
    public int hashCode() {
        return 0;
    }
    
    // todo...
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;        
        
        Klant k = (Klant)obj;
        return  k.getKlant_id() == this.getKlant_id() && 
                k.getHuisnummer() == this.getHuisnummer() &&
                k.getVoornaam().equals(this.getVoornaam()) && 
                k.getTussenvoegsel().equals(this.getTussenvoegsel()) &&
                k.getAchternaam().equals(this.getAchternaam()) &&
                k.getEmail().equals(this.getEmail()) &&
                k.getStraatnaam().equals(this.getStraatnaam()) &&
                k.getToevoeging().equals(this.getToevoeging()) &&
                k.getPostcode().equals(this.getPostcode()) &&
                k.getWoonplaats().equals(this.getWoonplaats());
    } 

    @Override
    public String toString() {
        return "Klant{" + "klant_id=" + super.primaryKeyProperty() + ", voornaam=" + voornaam + 
                ", tussenvoegsel=" + tussenvoegsel + ", achternaam=" + achternaam + 
                ", email=" + email + ", straatnaam=" + straatnaam + ", huisnummer=" + huisnummer + 
                ", toevoeging=" + toevoeging + ", postcode=" + postcode + 
                ", woonplaats=" + woonplaats + '}';
    }
}
