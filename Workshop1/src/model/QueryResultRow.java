package model;

/**
 * Class used by queryResult to represent rows. All properties are converted to String properties to
 * support easy display and editing in a TableView. (bestelling properties nog niet!)
 */
public class QueryResultRow {
    private Klant klant;
    private Bestelling bestelling;
    
    
    public QueryResultRow() {
        klant = new Klant();
        bestelling = new Bestelling();
    }
    
    public Klant getKlant() {
        return klant;
    }
    
    public void setKlant(Klant k) {
        klant = k;
    }
    
    public Bestelling getBestelling() {
        return bestelling;
    }
    
    public void setBestelling(Bestelling b) {
        bestelling = b;
    }

    @Override
    public int hashCode() {
        int hash = 23;
        int multiplier = 13;
        
        return  multiplier * hash +
                klant.hashCode() +
                bestelling.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        
        QueryResultRow qrr = (QueryResultRow)obj;
        return  qrr.klant.equals(this.klant) &&
                qrr.bestelling.equals(this.bestelling);
    }

    @Override
    public String toString() {
        return "QueryResultRow{" + "klant=" + klant + ", bestelling=" + bestelling + '}';
    }
}