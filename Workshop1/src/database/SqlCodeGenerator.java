
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
    
}
