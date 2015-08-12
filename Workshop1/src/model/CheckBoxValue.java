
package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CheckBoxValue {
    SimpleBooleanProperty value;
    
    public CheckBoxValue() {
        value = new SimpleBooleanProperty(false);
    }
    
    public boolean getValue() {
        return value.getValue();
    }
    
    public void setValue(boolean v) {
        value.setValue(v);
    }
    
    public BooleanProperty valueProperty() {
        return value;
    }
}
