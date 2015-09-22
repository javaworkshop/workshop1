package org.workshop1.dao.test.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.workshop1.model.Bestelling;
import org.workshop1.model.Klant;

public class SampleServiceImpl implements SampleService {
    @Autowired
    private List<Klant> klanten;
    @Autowired
    private List<Bestelling> bestellingen;
    
    @Override
    public Bestelling getBestelling(int i) {
        return bestellingen.get(i);
    }

    @Override
    public Klant getKlant(int i) {
        return klanten.get(i);
    }
    
    @Override
    public int sampleSizeKlanten() {
        return klanten.size();
    }
    
    @Override
    public int sampleSizeBestellingen() {
        return bestellingen.size();
    }
    
    public void setKlanten(List<Klant> klanten) {
        this.klanten = klanten;
    }
    
    public void setBestellingen(List<Bestelling> bestellingen) {
        this.bestellingen = bestellingen;
    }
}
