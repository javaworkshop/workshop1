package org.workshop1.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Parent class of all classes that are used to store data from the database.
 */
public abstract class Data {    
    
    private final SimpleIntegerProperty primaryKey;
    
    protected Data() {
        primaryKey = new SimpleIntegerProperty(0);
    }

    protected int getPrimaryKey() {
        return primaryKey.getValue();
    }

    protected void setPrimaryKey(int primaryKey) {
        this.primaryKey.setValue(primaryKey);
    }
    
    protected IntegerProperty primaryKeyProperty() {
        return primaryKey;
    } 
}