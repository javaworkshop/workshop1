package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class to store data from the bestelling table.
 */
public class Bestelling extends Data {
    private SimpleIntegerProperty klant_id; // De klant die de bestelling gedaan heeft
    private SimpleIntegerProperty artikel_id1;
    private SimpleIntegerProperty artikel_id2;
    private SimpleIntegerProperty artikel_id3;
    private SimpleStringProperty artikel_naam1;
    private SimpleStringProperty artikel_naam2;
    private SimpleStringProperty artikel_naam3;
    private SimpleIntegerProperty artikel_aantal1;
    private SimpleIntegerProperty artikel_aantal2;
    private SimpleIntegerProperty artikel_aantal3;
    private SimpleDoubleProperty artikel_prijs1;
    private SimpleDoubleProperty artikel_prijs2;
    private SimpleDoubleProperty artikel_prijs3;
    
    public Bestelling() {
        klant_id = new SimpleIntegerProperty(0);
        artikel_id1 = new SimpleIntegerProperty(0);
        artikel_id2 = new SimpleIntegerProperty(0);
        artikel_id3 = new SimpleIntegerProperty(0);
        artikel_naam1 = new SimpleStringProperty(null);
        artikel_naam2 = new SimpleStringProperty(null);
        artikel_naam3 = new SimpleStringProperty(null);
        artikel_aantal1 = new SimpleIntegerProperty(0);
        artikel_aantal2 = new SimpleIntegerProperty(0);
        artikel_aantal3 = new SimpleIntegerProperty(0);
        artikel_prijs1 = new SimpleDoubleProperty(0);
        artikel_prijs2 = new SimpleDoubleProperty(0);
        artikel_prijs3 = new SimpleDoubleProperty(0);
    }
    
    public int getBestelling_id() {
        return super.getPrimaryKey();
    }
    
    public void setBestelling_id(int id) {
        super.setPrimaryKey(id);
    }
    
    public IntegerProperty bestelling_idProperty() {
        return super.primaryKeyProperty();
    }

    public int getKlant_id() {
        return klant_id.getValue();
    }

    public void setKlant_id(int klant_id) {
        this.klant_id.setValue(klant_id);
    }
    
    public IntegerProperty klant_idProperty() {
        return klant_id;
    }

    public int getArtikel_id1() {
        return artikel_id1.getValue();
    }

    public void setArtikel_id1(int artikel_id1) {
        this.artikel_id1.setValue(artikel_id1);
    }
    
    public IntegerProperty artikel_id1Property() {
        return artikel_id1;
    }

    public int getArtikel_id2() {
        return artikel_id2.getValue();
    }

    public void setArtikel_id2(int artikel_id2) {
        this.artikel_id2.setValue(artikel_id2);
    }
    
    public IntegerProperty artikel_id2Property() {
        return artikel_id2;
    }

    public int getArtikel_id3() {
        return artikel_id3.getValue();
    }

    public void setArtikel_id3(int artikel_id3) {
        this.artikel_id3.setValue(artikel_id3);
    }
    
    public IntegerProperty artikel_id3Property() {
        return artikel_id3;
    }

    public String getArtikel_naam1() {
        return artikel_naam1.getValue();
    }

    public void setArtikel_naam1(String artikel_naam1) {
        this.artikel_naam1.setValue(artikel_naam1);
    }
    
    public StringProperty artikel_naam1Property() {
        return artikel_naam1;
    }

    public String getArtikel_naam2() {
        return artikel_naam2.getValue();
    }

    public void setArtikel_naam2(String artikel_naam2) {
        this.artikel_naam2.setValue(artikel_naam2);
    }
    
    public StringProperty artikel_naam2Property() {
        return artikel_naam2;
    }

    public String getArtikel_naam3() {
        return artikel_naam3.getValue();
    }

    public void setArtikel_naam3(String artikel_naam3) {
        this.artikel_naam3.setValue(artikel_naam3);
    }
    
    public StringProperty artikel_naam3Property() {
        return artikel_naam3;
    }

    public int getArtikel_aantal1() {
        return artikel_aantal1.getValue();
    }

    public void setArtikel_aantal1(int artikel_aantal1) {
        this.artikel_aantal1.setValue(artikel_aantal1);
    }
    
    public IntegerProperty artikel_aantal1Property() {
        return artikel_aantal1;
    }

    public int getArtikel_aantal2() {
        return artikel_aantal2.getValue();
    }

    public void setArtikel_aantal2(int artikel_aantal2) {
        this.artikel_aantal2.setValue(artikel_aantal2);
    }
    
    public IntegerProperty artikel_aantal2Property() {
        return artikel_aantal2;
    }

    public int getArtikel_aantal3() {
        return artikel_aantal3.getValue();
    }

    public void setArtikel_aantal3(int artikel_aantal3) {
        this.artikel_aantal3.setValue(artikel_aantal3);
    }
    
    public IntegerProperty artikel_aantal3Property() {
        return artikel_aantal3;
    }

    public double getArtikel_prijs1() {
        return artikel_prijs1.getValue();
    }

    public void setArtikel_prijs1(double artikel_prijs1) {
        this.artikel_prijs1.setValue(artikel_prijs1);
    }
    
    public DoubleProperty artikel_prijs1Property() {
        return artikel_prijs1;
    }

    public double getArtikel_prijs2() {
        return artikel_prijs2.getValue();
    }

    public void setArtikel_prijs2(double artikel_prijs2) {
        this.artikel_prijs2.setValue(artikel_prijs2);
    }
    
    public DoubleProperty artikel_prijs2Property() {
        return artikel_prijs2;
    }

    public double getArtikel_prijs3() {
        return artikel_prijs3.getValue();
    }

    public void setArtikel_prijs3(double artikel_prijs3) {
        this.artikel_prijs3.setValue(artikel_prijs3);
    }
    
    public DoubleProperty artikel_prijs3Property() {
        return artikel_prijs3;
    }

    // todo...
    @Override
    public int hashCode() {
        return 0;
    }
    
    // todo...
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
