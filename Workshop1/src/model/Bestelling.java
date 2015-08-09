package model;

/**
 * Klasse om bestellinggegevens in op te slaan.
 */
public class Bestelling extends Data {
    private int klant_id; // De klant die de bestelling gedaan heeft
    private int artikel_id1;
    private int artikel_id2;
    private int artikel_id3;
    private String artikel_naam1;
    private String artikel_naam2;
    private String artikel_naam3;
    private int artikel_aantal1;
    private int artikel_aantal2;
    private int artikel_aantal3;
    private double artikel_prijs1;
    private double artikel_prijs2;
    private double artikel_prijs3;
    
    public Bestelling() {
        klant_id = 0;
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
    
    public int getBestellingId() {
        return super.getPrimaryKey();
    }
    
    public void setBestellingId(int id) {
        super.setPrimaryKey(id);
    }

    public int getKlant_id() {
        return klant_id;
    }

    public void setKlant_id(int klant_id) {
        this.klant_id = klant_id;
    }

    public int getArtikel_id1() {
        return artikel_id1;
    }

    public void setArtikel_id1(int artikel_id1) {
        this.artikel_id1 = artikel_id1;
    }

    public int getArtikel_id2() {
        return artikel_id2;
    }

    public void setArtikel_id2(int artikel_id2) {
        this.artikel_id2 = artikel_id2;
    }

    public int getArtikel_id3() {
        return artikel_id3;
    }

    public void setArtikel_id3(int artikel_id3) {
        this.artikel_id3 = artikel_id3;
    }

    public String getArtikel_naam1() {
        return artikel_naam1;
    }

    public void setArtikel_naam1(String artikel_naam1) {
        this.artikel_naam1 = artikel_naam1;
    }

    public String getArtikel_naam2() {
        return artikel_naam2;
    }

    public void setArtikel_naam2(String artikel_naam2) {
        this.artikel_naam2 = artikel_naam2;
    }

    public String getArtikel_naam3() {
        return artikel_naam3;
    }

    public void setArtikel_naam3(String artikel_naam3) {
        this.artikel_naam3 = artikel_naam3;
    }

    public int getArtikel_aantal1() {
        return artikel_aantal1;
    }

    public void setArtikel_aantal1(int artikel_aantal1) {
        this.artikel_aantal1 = artikel_aantal1;
    }

    public int getArtikel_aantal2() {
        return artikel_aantal2;
    }

    public void setArtikel_aantal2(int artikel_aantal2) {
        this.artikel_aantal2 = artikel_aantal2;
    }

    public int getArtikel_aantal3() {
        return artikel_aantal3;
    }

    public void setArtikel_aantal3(int artikel_aantal3) {
        this.artikel_aantal3 = artikel_aantal3;
    }

    public double getArtikel_prijs1() {
        return artikel_prijs1;
    }

    public void setArtikel_prijs1(double artikel_prijs1) {
        this.artikel_prijs1 = artikel_prijs1;
    }

    public double getArtikel_prijs2() {
        return artikel_prijs2;
    }

    public void setArtikel_prijs2(double artikel_prijs2) {
        this.artikel_prijs2 = artikel_prijs2;
    }

    public double getArtikel_prijs3() {
        return artikel_prijs3;
    }

    public void setArtikel_prijs3(double artikel_prijs3) {
        this.artikel_prijs3 = artikel_prijs3;
    }

}
