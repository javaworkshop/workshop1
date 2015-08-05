package model;

/**
 * Parent klasse van alle klassen die bedoeld zijn voor opslag van database gegevens.
 */
public class Data {
    private int id;
    
    public Data() {
        id = 0;
    }
    
    public Data(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
}