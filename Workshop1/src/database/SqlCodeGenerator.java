
package database;

import model.*;

/**
 * Class that contains static methods to generate sql code from data objects. Cannot be 
 * instantiated.
 */
class SqlCodeGenerator {
    
    private SqlCodeGenerator(){}
    
    /**
     * Generates sql code to insert the given klant into the database.
     * @param klant klant to be inserted
     * @return      sql code to insert klant
     */
    static String generateKlantInsertionCode(Klant klant) {
        String sqlCode = "INSERT INTO klant VALUES(NULL, ";
        
        sqlCode += (klant.getVoornaam() == null) ? "NULL, " : "'" + 
            klant.getVoornaam() + "', ";
        sqlCode += (klant.getTussenvoegsel() == null) ? "NULL, " : "'" + 
            klant.getTussenvoegsel() + "', ";
        sqlCode += (klant.getAchternaam() == null) ? "NULL, " : "'" + 
            klant.getAchternaam() + "', ";
        sqlCode += (klant.getEmail() == null) ? "NULL, " : "'" + 
            klant.getEmail() + "', ";
        sqlCode += (klant.getStraatnaam() == null) ? "NULL, " : "'" + 
            klant.getStraatnaam() + "', ";
        sqlCode += (klant.getHuisnummer() == 0) ? "NULL, " : 
            klant.getHuisnummer() + ", ";
        sqlCode += (klant.getToevoeging() == null) ? "NULL, " : "'" + 
            klant.getToevoeging() + "', ";    
        sqlCode += (klant.getPostcode() == null) ? "NULL, " : "'" + 
            klant.getPostcode() + "', ";            
        sqlCode += (klant.getWoonplaats() == null) ? "NULL, " : "'" + 
            klant.getWoonplaats() + "')";
        
        return sqlCode;
    }
    
    /**
     * 
     * @param klant
     * @return 
     */
    static String generateKlantUpdateCode(Klant klant) {
        String sqlCode = "UPDATE klant SET ";
        
        sqlCode += (klant.getVoornaam() == null) ? "" : "voornaam = '" + 
            klant.getVoornaam() + "', ";
        sqlCode += (klant.getTussenvoegsel() == null) ? "" : "tussenvoegsel = '" + 
            klant.getTussenvoegsel() + "', ";
        sqlCode += (klant.getAchternaam() == null) ? "" : "achternaam = '" + 
            klant.getAchternaam() + "', ";
        sqlCode += (klant.getEmail() == null) ? "" : "email = '" + 
            klant.getEmail() + "', ";
        sqlCode += (klant.getStraatnaam() == null) ? "" : "straatnaam = '" + 
            klant.getStraatnaam() + "', ";
        sqlCode += (klant.getHuisnummer() == 0) ? "" : "huisnummer = " + 
            klant.getHuisnummer() + ", ";
        sqlCode += (klant.getToevoeging() == null) ? "" : "toevoeging = '" + 
            klant.getToevoeging() + "', ";    
        sqlCode += (klant.getPostcode() == null) ? "" : "postcode = '" + 
            klant.getPostcode() + "', ";            
        sqlCode += (klant.getWoonplaats() == null) ? "" : "woonplaats = '" + 
            klant.getWoonplaats() + "'";
        
        if(sqlCode.endsWith(", "))
            sqlCode = sqlCode.substring(0, sqlCode.length() - 2);
        
        sqlCode += " WHERE klant_id = " + klant.getKlant_id();
        
        return sqlCode;
    }
    
    /**
     * Generates sql code for selecting members of the klant table from the database. Code is
     * generated using attributes of a klant object.
     * @param klant
     * @return 
     */
    static String generateKlantSelectionCode(Klant klant) {
        String sqlCode = "SELECT * FROM klant WHERE ";
        
        sqlCode += (klant.getKlant_id() == 0) ? "" : "klant_id = " + 
            klant.getKlant_id() + " AND ";
        sqlCode += (klant.getVoornaam() == null) ? "" : "voornaam = '" + 
            klant.getVoornaam() + "' AND ";
        sqlCode += (klant.getTussenvoegsel() == null) ? "" : "tussenvoegsel = '" + 
            klant.getTussenvoegsel() + "' AND ";
        sqlCode += (klant.getAchternaam() == null) ? "" : "achternaam = '" + 
            klant.getAchternaam() + "' AND ";
        sqlCode += (klant.getEmail() == null) ? "" : "email = '" + 
            klant.getEmail() + "' AND ";
        sqlCode += (klant.getStraatnaam() == null) ? "" : "straatnaam = '" + 
            klant.getStraatnaam() + "' AND ";
        sqlCode += (klant.getHuisnummer() == 0) ? "" : "huisnummer = " + 
                klant.getHuisnummer() + " AND ";
        sqlCode += (klant.getToevoeging() == null) ? "" : "toevoeging = '" + 
            klant.getToevoeging() + "' AND ";    
        sqlCode += (klant.getPostcode() == null) ? "" : "postcode = '" + 
            klant.getPostcode() + "' AND ";            
        sqlCode += (klant.getWoonplaats() == null) ? "" : "woonplaats = '" + 
            klant.getWoonplaats() + "'";
        
        if(sqlCode.endsWith(" AND "))
            sqlCode = sqlCode.substring(0, sqlCode.length() - 5);
        
        return sqlCode;
    }   
    
    /**
     * TODO
     * @param bestelling
     * @return 
     */
    static String generateBestellingUpdateCode(Bestelling bestelling) {
        return "";
    }
    
    public static String generateBestellingInsertionCode(Bestelling bestelling){
            //implement 
            String sqlString = 
                    "Insert into bestelling (klant_id,  artikel_id1,  artikel_id2,  artikel_id3, "
                    + " artikel_naam1,  artikel_naam2,  artikel_naam3,  artikel_aantal1,  "
                    + "artikel_aantal2,  artikel_aantal3,  artikel_prijs1,  artikel_prijs2, "
                    + " artikel_prijs3) values (" +
                bestelling.getKlant_id() + ", " + bestelling.getArtikel_id1() + ", " + 
                    bestelling.getArtikel_id2() + ", " +bestelling.getArtikel_id3()
                    + ", \'" + bestelling.getArtikel_naam1() + "\', \'" + 
                    bestelling.getArtikel_naam2() + "\', \'" + bestelling.getArtikel_naam3() +
                    "\', " + bestelling.getArtikel_aantal1() + ", " + 
                    bestelling.getArtikel_aantal2() + ", " + bestelling.getArtikel_aantal3() + 
                    ", " + bestelling.getArtikel_prijs1() + ", " + bestelling.getArtikel_prijs2() +
                    ", " + bestelling.getArtikel_prijs3() + ")";
            
            
            return sqlString;
        }
        
        public static String generateArtikelUpdateCode(int bestellingId, int artikelId, 
                String artikelNaam, int artikelAantal, double artikelPrijs){
            String sqlString =   "  UPDATE   bestelling SET "
            //artikel id
            +  "artikel_id1 = ( CASE  WHEN (artikel_id1 is null and  IFNULL(artikel_id2,0) <> " + 
                    artikelId + "  and IFNULL(artikel_id3,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id1 END )," + 
            " artikel_id2 = (CASE  WHEN (artikel_id2 is null and IFNULL(artikel_id1,0) <> " + 
                    artikelId +  " and IFNULL(artikel_id3,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id2 END ), " + 
            "artikel_id3 =  (CASE  WHEN  (artikel_id3 is null and IFNULL(artikel_id1,0) <> " + 
                    artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id3  END ),"

            //artikel aantal
            + "artikel_aantal1 =  (CASE WHEN (artikel_id1 = " + artikelId +
            ")  	THEN (IFNULL(artikel_aantal1, 0) + " + artikelAantal + 
                    ") ELSE artikel_aantal1 END)," + 
            "artikel_aantal2 =(CASE WHEN (artikel_id2 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId +
            ")  	THEN (IFNULL(artikel_aantal2, 0) + " + artikelAantal + 
                    ") ELSE artikel_aantal2 END), " + 
            " artikel_aantal3 =  (CASE WHEN (artikel_id3 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId + 
                    " and IFNULL(artikel_id2,0) <> " + artikelId +
            ") 	THEN (IFNULL(artikel_aantal3, 0) + " + artikelAantal + 
                    ") ELSE artikel_aantal3 END),"

            //artikelnaam
            + " artikel_naam1 =  (CASE WHEN (artikel_id1 = " + artikelId + 
            ")  THEN \' " + artikelNaam + "\' ELSE artikel_naam1 END)," + 
            "artikel_naam2 =(CASE WHEN (artikel_id2 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId + 
            ")  THEN \' " + artikelNaam + "\' ELSE artikel_naam2 END)," + 
            "artikel_naam3 =  (CASE WHEN (artikel_id3 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId +
             ") THEN \' " + artikelNaam + "\' ELSE artikel_naam3 END),"

            //artikel prijs
            + "artikel_prijs1 =  (CASE WHEN (artikel_id1 = " + artikelId + 
            ")  THEN " + artikelPrijs + " ELSE artikel_prijs1 END)," +
            "artikel_prijs2 =( CASE WHEN (artikel_id2 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId + 
            ")  THEN " + artikelPrijs + " ELSE artikel_prijs2 END)," +
            "artikel_prijs3 =  (CASE WHEN (artikel_id3 = " + artikelId + 
                    " and IFNULL(artikel_id1,0) <> " + artikelId + 
                    " and IFNULL(artikel_id2,0) <> " + artikelId + 
            ")  THEN " + artikelPrijs + "  ELSE artikel_prijs3 END)" +
              "  WHERE  bestelling_id = " + bestellingId;
            
            return sqlString;
        }
    
}
