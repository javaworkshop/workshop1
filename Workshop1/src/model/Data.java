package model;

/**
 * Parent klasse van alle klassen die bedoeld zijn voor opslag van database gegevens.
 */
public abstract class Data {
    private int primaryKey;
    
    public Data() {
        primaryKey = 0;
    }
    
    public Data(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public static String[] getAttributeNames(Data data) {
        
        if(data instanceof Klant)
            return new String[]{"klant_id", "voornaam", "tussenvoegsel", "achternaam", "email", 
                "straatnaam", "huisnummer", "toevoeging", "postcode", "woonplaats"};
        else //if(data instanceof Bestelling)
            return new String[]{"bestelling_id", "klant_id", "artikel_id1", "artikel_id2", 
                "artikel_id3", "artikel_naam1", "artikel_naam2", "artikel_naam3", 
                "artikel_aantal1", "artikel_aantal2", "artikel_aantal3", "artikel_prijs1", 
                "artikel_prijs2", "artikel_prijs3"};
    }
    
}