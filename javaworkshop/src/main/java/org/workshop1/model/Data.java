package org.workshop1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Parent class of all classes that are used to store data from the database.
 */
public abstract class Data implements Serializable {    
    
    private final SimpleIntegerProperty primaryKey;
    
    public static int indexOfPrimaryKey(ArrayList<? extends Data> dataList, Data data) {
        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).getPrimaryKey() == data.getPrimaryKey())
                return i;
        }
        return -1;
    }
    
    public static int indexOfPrimaryKey(ArrayList<? extends Data> dataList, int primaryKey) {
        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).getPrimaryKey() == primaryKey)
                return i;
        }
        return -1;
    }
    
    protected Data() {
        primaryKey = new SimpleIntegerProperty(0);
    }

    protected int getPrimaryKey() {
        return primaryKey.getValue();
    }

    protected IntegerProperty primaryKeyProperty() {
        return primaryKey;
    }
    
    protected void setPrimaryKey(int primaryKey) {
        this.primaryKey.setValue(primaryKey);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        Data d = (Data)obj;
        return  d.getPrimaryKey() == this.getPrimaryKey();
    }

    @Override
    public String toString() {
        return "Data{" + "primaryKey=" + primaryKey + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.primaryKey);
        return hash;
    }
    
}