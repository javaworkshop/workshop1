
package org.workshop1.model;

public class Artikel {
    int bestelling_id;
    int artikel_id;
    String artikel_naam;
    int artikel_aantal;
    double artikel_prijs;
    
    public Artikel() {
        bestelling_id = 0;
        artikel_id = 0;
        artikel_naam = "";
        artikel_aantal = 0;
        artikel_prijs = 0;
    }

    public int getBestelling_id() {
        return bestelling_id;
    }

    public void setBestelling_id(int bestelling_id) {
        this.bestelling_id = bestelling_id;
    }

    public int getArtikel_id() {
        return artikel_id;
    }

    public void setArtikel_id(int artikel_id) {
        this.artikel_id = artikel_id;
    }

    public String getArtikel_naam() {
        return artikel_naam;
    }

    public void setArtikel_naam(String artikel_naam) {
        this.artikel_naam = artikel_naam;
    }

    public int getArtikel_aantal() {
        return artikel_aantal;
    }

    public void setArtikel_aantal(int artikel_aantal) {
        this.artikel_aantal = artikel_aantal;
    }

    public double getArtikel_prijs() {
        return artikel_prijs;
    }

    public void setArtikel_prijs(double artikel_prijs) {
        this.artikel_prijs = artikel_prijs;
    }
}
