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
    
}