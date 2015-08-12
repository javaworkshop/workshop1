
package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 */
public abstract class DataDisplayRow {
    SimpleBooleanProperty update;
    SimpleBooleanProperty delete;
    
    public abstract StringProperty klant_idProperty();
    public abstract StringProperty voornaamProperty();
    public abstract StringProperty tussenvoegselProperty();
    public abstract StringProperty achternaamProperty();
    public abstract StringProperty emailProperty();
    public abstract StringProperty straatnaamProperty();
    public abstract StringProperty huisnummerProperty();
    public abstract StringProperty toevoegingProperty();
    public abstract StringProperty postcodeProperty();
    public abstract StringProperty woonplaatsProperty();
    
    DataDisplayRow() {
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
    }
    
    public boolean getUpdate() {
        return update.getValue();
    }
    
    public void setValue(boolean bool) {
        update.setValue(bool);
    }
    
    public BooleanProperty updateProperty() {
        return update;
    }
    
    public boolean getDelete() {
        return delete.getValue();
    }
    
    public void setDelete(boolean bool) {
        delete.setValue(bool);
    }
    
    public BooleanProperty deleteProperty() {
        return delete;
    }
}
