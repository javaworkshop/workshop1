package org.workshop1.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.workshop1.database.Column;
import org.workshop1.database.Entity;
import org.workshop1.database.Id;
import org.workshop1.database.Table;

/**
 * Class to store data from the bestelling table.
 */
@Entity()
@Table(name = "bestellingen")
public class Bestelling implements Data, Comparable<Bestelling> {
    @Id(name = "bestelling_id", length = 10) private final SimpleIntegerProperty bestelling_id;
    @Column(name = "klant_id", length = 10) private final SimpleIntegerProperty klant_id; // De klant die de bestelling gedaan heeft
    @Column(name = "artikel_id1", length = 10) private final SimpleIntegerProperty artikel_id1;
    @Column(name = "artikel_id2", length = 10) private final SimpleIntegerProperty artikel_id2;
    @Column(name = "artikel_id3", length = 10) private final SimpleIntegerProperty artikel_id3;
    @Column(name = "artikel_naam1", length = 45) private final SimpleStringProperty artikel_naam1;
    @Column(name = "artikel_naam2", length = 45) private final SimpleStringProperty artikel_naam2;
    @Column(name = "artikel_naam3", length = 45) private final SimpleStringProperty artikel_naam3;
    @Column(name = "artikel_aantal1", length = 10) private final SimpleIntegerProperty artikel_aantal1;
    @Column(name = "artikel_aantal2", length = 10) private final SimpleIntegerProperty artikel_aantal2;
    @Column(name = "artikel_aantal3", length = 10) private final SimpleIntegerProperty artikel_aantal3;
    @Column(name = "artikel_prijs1", length = 10) private final SimpleDoubleProperty artikel_prijs1;
    @Column(name = "artikel_prijs2", length = 10) private final SimpleDoubleProperty artikel_prijs2;
    @Column(name = "artikel_prijs3", length = 10) private final SimpleDoubleProperty artikel_prijs3;
    
    public Bestelling() {
        bestelling_id = new SimpleIntegerProperty(0);
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
    
    @Override
    public int compareTo(Bestelling b) {
        if(b.getBestelling_id() < this.getBestelling_id())
            return 1;
        else if(b.getBestelling_id() > this.getBestelling_id())
            return -1;
        else
            return 0;
    }
    
    public IntegerProperty artikel_aantal1Property() {
        return artikel_aantal1;
    }
    
    public IntegerProperty artikel_aantal2Property() {
        return artikel_aantal2;
    }
    
    public IntegerProperty artikel_aantal3Property() {
        return artikel_aantal3;
    }

    public IntegerProperty artikel_id1Property() {
        return artikel_id1;
    }

    public IntegerProperty artikel_id2Property() {
        return artikel_id2;
    }
    
    public IntegerProperty artikel_id3Property() {
        return artikel_id3;
    }

    public StringProperty artikel_naam1Property() {
        return artikel_naam1;
    }

    public StringProperty artikel_naam2Property() {
        return artikel_naam2;
    }
    
    public StringProperty artikel_naam3Property() {
        return artikel_naam3;
    }

    public DoubleProperty artikel_prijs1Property() {
        return artikel_prijs1;
    }

    public DoubleProperty artikel_prijs2Property() {
        return artikel_prijs2;
    }
    
    public DoubleProperty artikel_prijs3Property() {
        return artikel_prijs3;
    }

    public IntegerProperty bestelling_idProperty() {
        return bestelling_idProperty();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        Bestelling b = (Bestelling)obj;
        return  b.getBestelling_id() == this.getBestelling_id() &&
                b.getKlant_id() == this.getKlant_id() &&
                b.getArtikel_id1() == this.getArtikel_id1() &&
                b.getArtikel_id2() == this.getArtikel_id2() &&
                b.getArtikel_id3() == this.getArtikel_id3() &&
                b.getArtikel_aantal1() == this.getArtikel_aantal1() &&
                b.getArtikel_aantal2() == this.getArtikel_aantal2() &&
                b.getArtikel_aantal3() == this.getArtikel_aantal3() &&
                Math.abs(b.getArtikel_prijs1() - this.getArtikel_prijs1()) <= 0.001 &&
                Math.abs(b.getArtikel_prijs2() - this.getArtikel_prijs2()) <= 0.001 &&
                Math.abs(b.getArtikel_prijs3() - this.getArtikel_prijs3()) <= 0.001 &&
                (b.getArtikel_naam1() == null && this.getArtikel_naam1() == null ||
                b.getArtikel_naam1().equals(this.getArtikel_naam1())) &&
                (b.getArtikel_naam2() == null && this.getArtikel_naam2() == null ||
                b.getArtikel_naam2().equals(this.getArtikel_naam2())) &&
                (b.getArtikel_naam3() == null && this.getArtikel_naam3() == null ||
                b.getArtikel_naam3().equals(this.getArtikel_naam3()));
    }
    
    public int getArtikel_aantal1() {
        return artikel_aantal1.getValue();
    }

    public int getArtikel_aantal2() {
        return artikel_aantal2.getValue();
    }

    public int getArtikel_aantal3() {
        return artikel_aantal3.getValue();
    }
    
    public int getArtikel_id1() {
        return artikel_id1.getValue();
    }

    public int getArtikel_id2() {
        return artikel_id2.getValue();
    }

    public int getArtikel_id3() {
        return artikel_id3.getValue();
    }
    
    public String getArtikel_naam1() {
        return artikel_naam1.getValue();
    }

    public String getArtikel_naam2() {
        return artikel_naam2.getValue();
    }

    public String getArtikel_naam3() {
        return artikel_naam3.getValue();
    }
    
    public double getArtikel_prijs1() {
        return artikel_prijs1.getValue();
    }

    public double getArtikel_prijs2() {
        return artikel_prijs2.getValue();
    }

    public double getArtikel_prijs3() {
        return artikel_prijs3.getValue();
    }
    
    public int getBestelling_id() {
        return bestelling_id.getValue();
    }

    public int getKlant_id() {
        return klant_id.getValue();
    }

    @Override
    public int hashCode() {
        int hash = 13;
        int multiplier = 7;
        
        return  multiplier * hash + 
                getBestelling_id() +
                getKlant_id() +
                getArtikel_id1() +
                getArtikel_id2() +
                getArtikel_id3() +
                getArtikel_naam1().hashCode() +
                getArtikel_naam2().hashCode() +
                getArtikel_naam3().hashCode() +
                getArtikel_aantal1() +
                getArtikel_aantal2() +
                getArtikel_aantal3() +
                (int)getArtikel_prijs1() +
                (int)getArtikel_prijs2() +
                (int)getArtikel_prijs3();
    }
    
    public IntegerProperty klant_idProperty() {
        return klant_id;
    }

    public void setArtikel_aantal1(int artikel_aantal1) {
        this.artikel_aantal1.setValue(artikel_aantal1);
    }

    public void setArtikel_aantal2(int artikel_aantal2) {
        this.artikel_aantal2.setValue(artikel_aantal2);
    }
    
    public void setArtikel_aantal3(int artikel_aantal3) {
        this.artikel_aantal3.setValue(artikel_aantal3);
    }

    public void setArtikel_id1(int artikel_id1) {
        this.artikel_id1.setValue(artikel_id1);
    }

    public void setArtikel_id2(int artikel_id2) {
        this.artikel_id2.setValue(artikel_id2);
    }
    
    public void setArtikel_id3(int artikel_id3) {
        this.artikel_id3.setValue(artikel_id3);
    }

    public void setArtikel_naam1(String artikel_naam1) {
        this.artikel_naam1.setValue(artikel_naam1);
    }

    public void setArtikel_naam2(String artikel_naam2) {
        this.artikel_naam2.setValue(artikel_naam2);
    }
    
    public void setArtikel_naam3(String artikel_naam3) {
        this.artikel_naam3.setValue(artikel_naam3);
    }

    public void setArtikel_prijs1(double artikel_prijs1) {
        this.artikel_prijs1.setValue(artikel_prijs1);
    }

    public void setArtikel_prijs2(double artikel_prijs2) {
        this.artikel_prijs2.setValue(artikel_prijs2);
    }
    
    public void setArtikel_prijs3(double artikel_prijs3) {
        this.artikel_prijs3.setValue(artikel_prijs3);
    }

    public void setBestelling_id(int id) {
        bestelling_id.setValue(id);
    }
    
    public void setKlant_id(int klant_id) {
        this.klant_id.setValue(klant_id);
    }

    @Override
    public String toString() {
        return "Bestelling{" + "bestelling_id=" + bestelling_id + "klant_id=" + klant_id
                + ", artikel_id1=" + artikel_id1 + ", artikel_id2=" + artikel_id2 +
                ", artikel_id3=" + artikel_id3 + ", artikel_naam1=" + artikel_naam1 + 
                ", artikel_naam2=" + artikel_naam2 + ", artikel_naam3=" + artikel_naam3 + 
                ", artikel_aantal1=" + artikel_aantal1 + ", artikel_aantal2=" + artikel_aantal2 + 
                ", artikel_aantal3=" + artikel_aantal3 + ", artikel_prijs1=" + artikel_prijs1 + 
                ", artikel_prijs2=" + artikel_prijs2 + ", artikel_prijs3=" + artikel_prijs3 + '}';
    }
}
