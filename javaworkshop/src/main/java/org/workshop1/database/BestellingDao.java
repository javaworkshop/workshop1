package org.workshop1.database;

import java.util.ArrayList;
import org.workshop1.model.Bestelling;

public interface BestellingDao {
    public Bestelling read(int bestelling_id);
    public ArrayList<Bestelling> readAll();
    
    public void add(Bestelling bestelling);
            
    public void update(Bestelling bestelling);
    
    public void delete(Bestelling bestelling);
}
