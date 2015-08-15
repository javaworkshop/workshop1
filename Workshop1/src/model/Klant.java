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
    private SimpleStringProperty voornaam;
    private SimpleStringProperty tussenvoegsel;
    private SimpleStringProperty achternaam;
    private SimpleStringProperty email;
    private SimpleStringProperty straatnaam;
    private SimpleIntegerProperty huisnummer;
    private SimpleStringProperty toevoeging;
    private SimpleStringProperty postcode;
    private SimpleStringProperty woonplaats;
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
