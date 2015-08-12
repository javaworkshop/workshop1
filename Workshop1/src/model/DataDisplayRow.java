
package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class meant to make editing and displaying a QueryResultRow in a TableView easier. Also contains
 * BooleanProperties to keep track of the state of the update and delete checkboxes.
 */
public class DataDisplayRow {
    SimpleBooleanProperty update;
    SimpleBooleanProperty delete;
    
    QueryResultRow queryResultRow;
    
    private SimpleStringProperty klant_id;  // Integer and double properties are converted 
    private SimpleStringProperty huisnummer;// to String properties to support easy display
                                            // and editing in a TableView.   
    
    public DataDisplayRow(QueryResultRow queryResultRow) {
        update = new SimpleBooleanProperty(false);
        delete = new SimpleBooleanProperty(false);
        this.queryResultRow = queryResultRow;
        klant_id = new SimpleStringProperty(queryResultRow.getKlant().getKlant_id() + "");
        huisnummer = new SimpleStringProperty(queryResultRow.getKlant().getHuisnummer() + "");
    }

    public QueryResultRow getQueryResultRow() {
        return queryResultRow;
    }

    public void setQueryResultRow(QueryResultRow queryResultRow) {
        this.queryResultRow = queryResultRow;
    }
    
    public StringProperty klant_idProperty() {
        return klant_id;
    }
    public StringProperty voornaamProperty() {
        return queryResultRow.voornaamProperty();
    }
    public StringProperty tussenvoegselProperty() {
        return queryResultRow.tussenvoegselProperty();
    }
    public StringProperty achternaamProperty() {
        return queryResultRow.achternaamProperty();
    }
    public StringProperty emailProperty() {
        return queryResultRow.emailProperty();
    }
    
    public StringProperty straatnaamProperty() {
        return queryResultRow.straatnaamProperty();
    }
    
    public StringProperty huisnummerProperty() {
        return huisnummer;
    }
    
    public StringProperty toevoegingProperty() {
        return queryResultRow.toevoegingProperty();
    }
    
    public StringProperty postcodeProperty() {
        return queryResultRow.postcodeProperty();
    }
    
    public StringProperty woonplaatsProperty() {
        return queryResultRow.woonplaatsProperty();
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
