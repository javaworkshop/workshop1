package model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Parent klasse van alle klassen die bedoeld zijn voor opslag van database gegevens.
 */
public abstract class Data {
    public static final LinkedHashSet<String> KLANT_ATTRIBUTES = new LinkedHashSet<>(Arrays.asList(
            new String[]{"klant_id", "voornaam", "tussenvoegsel", "achternaam", "email", 
            "straatnaam", "huisnummer", "toevoeging", "postcode", "woonplaats"}));
    public static final LinkedHashSet<String> BESTELLING_ATTRIBUTES = new LinkedHashSet<>(
            Arrays.asList(new String[]{"bestelling_id", "klant_id", "artikel_id1", "artikel_naam1", 
                "artikel_aantal1", "artikel_prijs1", "artikel_id2", "artikel_naam2", 
                "artikel_aantal2", "artikel_prijs2", "artikel_id3", "artikel_naam3", 
                "artikel_aantal3", "artikel_prijs3"}));
    
    private SimpleIntegerProperty primaryKey;
    
    protected Data() {
        primaryKey = new SimpleIntegerProperty(0);
    }

    protected int getPrimaryKey() {
        return primaryKey.getValue();
    }

    protected void setPrimaryKey(int primaryKey) {
        this.primaryKey.setValue(primaryKey);
    }
    
    protected IntegerProperty primaryKeyProperty() {
        return primaryKey;
    }
    
    public static String[] getAttributeNames(Data data) {        
        if(data instanceof Klant)
            return (String[])KLANT_ATTRIBUTES.toArray();
        else //if(data instanceof Bestelling)
            return (String[])BESTELLING_ATTRIBUTES.toArray();
    }
    
}