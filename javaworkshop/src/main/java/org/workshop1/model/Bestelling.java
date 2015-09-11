package org.workshop1.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class to store data from the bestelling table.
 */
@Entity
@Table(name = "bestelling")
public class Bestelling implements Data, Comparable<Bestelling> {
    @Id
    @Column(name="bestelling_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int bestelling_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="klant_id")
    Klant klant;  
    @Column(name = "artikel_id1", length = 10)
    private int artikel_id1;
    @Column(name = "artikel_id2", length = 10) 
    private int artikel_id2;
    @Column(name = "artikel_id3", length = 10) 
    private int artikel_id3;
    @Column(name = "artikel_naam1", length = 45) 
    private String artikel_naam1;
    @Column(name = "artikel_naam2", length = 45) 
    private String artikel_naam2;
    @Column(name = "artikel_naam3", length = 45) 
    private String artikel_naam3;
    @Column(name = "artikel_aantal1", length = 10) 
    private int artikel_aantal1;
    @Column(name = "artikel_aantal2", length = 10) 
    private int artikel_aantal2;
    @Column(name = "artikel_aantal3", length = 10) 
    private int artikel_aantal3;
    @Column(name = "artikel_prijs1", length = 10) 
    private double artikel_prijs1;
    @Column(name = "artikel_prijs2", length = 10) 
    private double artikel_prijs2;
    @Column(name = "artikel_prijs3", length = 10) 
    private double artikel_prijs3;
    
    public Bestelling() {
        bestelling_id = 0;
        artikel_id1 = 0;
        artikel_id2 = 0;
        artikel_id3 = 0;
        artikel_naam1 = null;
        artikel_naam2 = null;
        artikel_naam3 = null;
        artikel_aantal1 = 0;
        artikel_aantal2 = 0;
        artikel_aantal3 = 0;
        artikel_prijs1 = 0;
        artikel_prijs2 = 0;
        artikel_prijs3 = 0;
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

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        Bestelling b = (Bestelling)obj;
        return  b.getBestelling_id() == this.getBestelling_id() &&
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
        return artikel_aantal1;
    }

    public int getArtikel_aantal2() {
        return artikel_aantal2;
    }

    public int getArtikel_aantal3() {
        return artikel_aantal3;
    }
    
    public int getArtikel_id1() {
        return artikel_id1;
    }

    public int getArtikel_id2() {
        return artikel_id2;
    }

    public int getArtikel_id3() {
        return artikel_id3;
    }
    
    public String getArtikel_naam1() {
        return artikel_naam1;
    }

    public String getArtikel_naam2() {
        return artikel_naam2;
    }

    public String getArtikel_naam3() {
        return artikel_naam3;
    }
    
    public double getArtikel_prijs1() {
        return artikel_prijs1;
    }

    public double getArtikel_prijs2() {
        return artikel_prijs2;
    }

    public double getArtikel_prijs3() {
        return artikel_prijs3;
    }
    
    public int getBestelling_id() {
        return bestelling_id;
    }
    
     public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        int multiplier = 7;
        
        return  multiplier * hash + 
                getBestelling_id() +
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

    public void setArtikel_aantal1(int artikel_aantal1) {
        this.artikel_aantal1 = artikel_aantal1;
    }

    public void setArtikel_aantal2(int artikel_aantal2) {
        this.artikel_aantal2 = artikel_aantal2;
    }
    
    public void setArtikel_aantal3(int artikel_aantal3) {
        this.artikel_aantal3 = artikel_aantal3;
    }

    public void setArtikel_id1(int artikel_id1) {
        this.artikel_id1 = artikel_id1;
    }

    public void setArtikel_id2(int artikel_id2) {
        this.artikel_id2 = artikel_id2;
    }
    
    public void setArtikel_id3(int artikel_id3) {
        this.artikel_id3 = artikel_id3;
    }

    public void setArtikel_naam1(String artikel_naam1) {
        this.artikel_naam1 = artikel_naam1;
    }

    public void setArtikel_naam2(String artikel_naam2) {
        this.artikel_naam2 = artikel_naam2;
    }
    
    public void setArtikel_naam3(String artikel_naam3) {
        this.artikel_naam3 = artikel_naam3;
    }

    public void setArtikel_prijs1(double artikel_prijs1) {
        this.artikel_prijs1 = artikel_prijs1;
    }

    public void setArtikel_prijs2(double artikel_prijs2) {
        this.artikel_prijs2 = artikel_prijs2;
    }
    
    public void setArtikel_prijs3(double artikel_prijs3) {
        this.artikel_prijs3 = artikel_prijs3;
    }

    public void setBestelling_id(int id) {
        bestelling_id = id;
    }
    
    @Override
    public String toString() {
        return "Bestelling{" + "bestelling_id=" + bestelling_id + "klant_id=" +
                ", artikel_id1=" + artikel_id1 + ", artikel_id2=" + artikel_id2 +
                ", artikel_id3=" + artikel_id3 + ", artikel_naam1=" + artikel_naam1 + 
                ", artikel_naam2=" + artikel_naam2 + ", artikel_naam3=" + artikel_naam3 + 
                ", artikel_aantal1=" + artikel_aantal1 + ", artikel_aantal2=" + artikel_aantal2 + 
                ", artikel_aantal3=" + artikel_aantal3 + ", artikel_prijs1=" + artikel_prijs1 + 
                ", artikel_prijs2=" + artikel_prijs2 + ", artikel_prijs3=" + artikel_prijs3 + '}';
    }
}
