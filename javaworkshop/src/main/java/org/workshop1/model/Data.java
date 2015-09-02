package org.workshop1.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Marker interface for all classes that represent storage entities.
 */
public interface Data extends Serializable {    
    public static int indexOfPrimaryKey(ArrayList<? extends Data> dataList, int id) {
        if(dataList.get(0) instanceof Klant) {            
            for(int i = 0; i < dataList.size(); i++) {
                if(((Klant)dataList.get(i)).getKlant_id() == id)
                    return i;
            }
        }
        else if(dataList.get(0) instanceof Bestelling) {
            for(int i = 0; i < dataList.size(); i++) {
                if(((Bestelling)dataList.get(i)).getKlant_id() == id)
                    return i;
            }
        }
        return -1;
    }
}