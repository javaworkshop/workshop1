package org.workshop1.model;

import java.util.ArrayList;

public class KlantList {
    private ArrayList<Klant> klanten;
    
    public KlantList() {
        klanten = new ArrayList<>();
    }
    
    public KlantList(ArrayList<Klant> klanten) {
        this.klanten = klanten;
    }
    
    public void setKlanten(ArrayList<Klant> klanten) {
        this.klanten = klanten;
    }
    
    public ArrayList<Klant> getKlanten() {
        return klanten;
    }
    
    public void add(Klant klant) {
        klanten.add(klant);
    }
}
