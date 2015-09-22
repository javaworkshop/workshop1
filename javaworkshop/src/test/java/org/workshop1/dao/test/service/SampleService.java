package org.workshop1.dao.test.service;

import org.workshop1.model.Bestelling;
import org.workshop1.model.Klant;

public interface SampleService {
    Klant getKlant(int id);
    Bestelling getBestelling(int id);
    int sampleSizeKlanten();
    int sampleSizeBestellingen();
}
