
package database;


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Artikel;
import model.Bestelling;
import model.Klant;

/**
 * Class that contains static methods to generate sql code from data objects. Cannot be 
 * instantiated.
 */
public class SqlCodeGenerator {
    
    private SqlCodeGenerator(){}
    
    /**
     * Generates sql code to insert the given klant into the database.
     * @param klant klant to be inserted
     * @return      sql code to insert klant
     */
    
    //methode die gebruik maakt van annotation
    public static String buildAnnotationInsertStatement(Object object){
        int variableToInsert = 0;
        String sqlTableName = object.getClass().getSimpleName().toUpperCase();
        String buildSqlStatement = "INSERT INTO " + sqlTableName + "("; 
        String valueFieldEnd = "values (";
        Field[] declaredFields = object.getClass().getDeclaredFields();
        
           for (Field field : declaredFields){
            try{
                    field.setAccessible(true);
                    if (field.get(object) != null && !isPrimitiveZero(field.get(object))){
                        List<Annotation> annotations = Arrays.asList(field.getDeclaredAnnotations());
                        //misschien ook iets anders doen met annotatie @id
                        //the onderstaande if methode pakt de annotaties nog niet
                        if (annotations.contains(database.Column.class)|| annotations.contains(database.Id.class)){
                            variableToInsert++;
                            if (variableToInsert > 1){
                                buildSqlStatement += ", ";
                                valueFieldEnd += ", ";
                            }
                            buildSqlStatement += field.getName();
                            if (field.get(object) instanceof SimpleStringProperty) {
                                valueFieldEnd += "\'" + ((SimpleStringProperty)field.get(object)).get() + "\'";
                            }
                            else if (field.get(object) instanceof SimpleIntegerProperty){
                                valueFieldEnd += ((SimpleIntegerProperty)field.get(object)).get();
                            }
                            else if (field.get(object) instanceof SimpleDoubleProperty){
                                valueFieldEnd += ((SimpleDoubleProperty)field.get(object)).get();
                            }
                            else{
                                valueFieldEnd += field.get(object);
                            }
                        }
                    }
            }
            catch (IllegalArgumentException | IllegalAccessException | SecurityException e){
                e.printStackTrace();
            }
        }
           //test
           String returnString = buildSqlStatement + ") " + valueFieldEnd + ")";
           System.out.println(returnString);
        return returnString;
    }
    
    //methode die gebruikt maakt van reflection en alle typen objecten accepteert
    public static String buildInsertStatement(Object object){
        int variableToInsert = 0;
        String sqlTableName = object.getClass().getSimpleName().toUpperCase();
        String buildSqlStatement = "INSERT INTO " + sqlTableName + "("; 
        String valueFieldEnd = "values (";
        Field[] declaredFields = object.getClass().getDeclaredFields();
        
        for (Field field : declaredFields){
            try{
                    field.setAccessible(true);
                    if (field.get(object) != null && !isPrimitiveZero(field.get(object))){
                        variableToInsert++;
                        if (variableToInsert > 1){
                            buildSqlStatement += ", ";
                            valueFieldEnd += ", ";
                        }
                        buildSqlStatement += field.getName();
                        if (field.get(object) instanceof SimpleStringProperty) {
                            valueFieldEnd += "\'" + ((SimpleStringProperty)field.get(object)).get() + "\'";
                        }
                        else if (field.get(object) instanceof SimpleIntegerProperty){
                            valueFieldEnd += ((SimpleIntegerProperty)field.get(object)).get();
                        }
                        else if (field.get(object) instanceof SimpleDoubleProperty){
                            valueFieldEnd += ((SimpleDoubleProperty)field.get(object)).get();
                        }
                        else{
                            valueFieldEnd += field.get(object);
                        }
                    }
            }
            catch (IllegalArgumentException | IllegalAccessException | SecurityException e){
                e.printStackTrace();
            }
        }
           //test
           String returnString = buildSqlStatement + ") " + valueFieldEnd + ")";
           System.out.println(returnString);
        return returnString;
    }
    
    
    //methode die gebruikt maakt van reflection    
    public static String buildInsertStatementKlant(Klant object){
        int variableToInsert = 0;
        String sqlTableName = Klant.class.getSimpleName().toUpperCase();
        String buildSqlStatement = "INSERT INTO " + sqlTableName + "("; String valueFieldEnd = "values (";
        Field[] declaredFields = Klant.class.getDeclaredFields();
        
        for (Field field : declaredFields){
            try{
                    field.setAccessible(true);
                    if (field.get(object) != null && !isPrimitiveZero(field.get(object))){
                        variableToInsert++;
                        if (variableToInsert > 1){
                            buildSqlStatement += ", ";
                            valueFieldEnd += ", ";
                        }
                        buildSqlStatement += field.getName();
                        if (field.get(object) instanceof SimpleStringProperty) {
                            valueFieldEnd += "\'" + ((SimpleStringProperty)field.get(object)).get() + "\'";
                        }
                        else if (field.get(object) instanceof SimpleIntegerProperty){
                            valueFieldEnd += ((SimpleIntegerProperty)field.get(object)).get();
                        }
                        else if (field.get(object) instanceof SimpleDoubleProperty){
                            valueFieldEnd += ((SimpleDoubleProperty)field.get(object)).get();
                        }
                        else{
                            valueFieldEnd += field.get(object);
                        }
                    }
            }
            catch (IllegalArgumentException | IllegalAccessException | SecurityException e){
                e.printStackTrace();
            }
        }
           //test
           String returnString = buildSqlStatement + ") " + valueFieldEnd + ")";
           System.out.println(returnString);
        return returnString;
    }
    
    private static boolean isPrimitiveZero(Object object) { 
    boolean isPrimitiveZero = false; 
    if (object instanceof Long) {
        if ((Long) object == 0) { 
            isPrimitiveZero = true; 
        }
    }
    else if (object instanceof Integer) {
        if ((Integer) object == 0) {
            isPrimitiveZero = true; 
        }
    }
    else if (object instanceof Float) {
        if ((Float) object == 0.0) {
            isPrimitiveZero = true; 
        }
    }
    else if (object instanceof Double) {
        if ((Double) object == 0.0) {
            isPrimitiveZero = true; 
        }
    }
    return isPrimitiveZero;
    }
    
    
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
        String sqlCode = "UPDATE bestelling SET ";
        
        sqlCode += (bestelling.getKlant_id() == 0) ? "" : "klant_id = " + 
            bestelling.getKlant_id() + ", ";
        sqlCode += (bestelling.getArtikel_id1() == 0) ? "" : "artikel_id1 = " + 
            bestelling.getArtikel_id1() + ", ";
        sqlCode += (bestelling.getArtikel_naam1()== null) ? "" : "artikel_naam1 = '" + 
            bestelling.getArtikel_naam1() + "', ";
        sqlCode += (bestelling.getArtikel_aantal1() == 0) ? "" : "artikel_aantal1 = " + 
            bestelling.getArtikel_aantal1() + ", ";
        sqlCode += (bestelling.getArtikel_prijs1() == 0) ? "" : "artikel_prijs1 = " + 
            bestelling.getArtikel_prijs1() + ", ";
        sqlCode += (bestelling.getArtikel_id2() == 0) ? "" : "artikel_id2 = " + 
            bestelling.getArtikel_id2() + ", ";
        sqlCode += (bestelling.getArtikel_naam2()== null) ? "" : "artikel_naam2 = '" + 
            bestelling.getArtikel_naam2() + "', ";
        sqlCode += (bestelling.getArtikel_aantal2() == 0) ? "" : "artikel_aantal2 = " + 
            bestelling.getArtikel_aantal2() + ", ";
        sqlCode += (bestelling.getArtikel_prijs2() == 0) ? "" : "artikel_prijs2 = " + 
            bestelling.getArtikel_prijs2() + ", ";
        sqlCode += (bestelling.getArtikel_id3() == 0) ? "" : "artikel_id3 = " + 
            bestelling.getArtikel_id3() + ", ";
        sqlCode += (bestelling.getArtikel_naam3()== null) ? "" : "artikel_naam3 = '" + 
            bestelling.getArtikel_naam3() + "', ";
        sqlCode += (bestelling.getArtikel_aantal3() == 0) ? "" : "artikel_aantal3 = " + 
            bestelling.getArtikel_aantal3() + ", ";
        sqlCode += (bestelling.getArtikel_prijs3() == 0) ? "" : "artikel_prijs3 = " + 
            bestelling.getArtikel_prijs3();
        
        if(sqlCode.endsWith(", "))
            sqlCode = sqlCode.substring(0, sqlCode.length() - 2);
        
        sqlCode += " WHERE bestelling_id = " + bestelling.getBestelling_id();
        
        return sqlCode;
    }
    
    public static String generateBestellingInsertionCode(Bestelling bestelling){
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
            
            //test
            System.out.println(sqlString);
            return sqlString;
        }
        
        public static String generateArtikelUpdateCode(Artikel a){
            int bestellingId = a.getBestelling_id();
            int artikelId = a.getArtikel_id();
            String artikelNaam = a.getArtikel_naam();
            int artikelAantal = a.getArtikel_aantal();
            double artikelPrijs = a.getArtikel_prijs();
            
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
                    " and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " 
                    + artikelId +
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

