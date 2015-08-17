package database;

import java.sql.SQLException;
import java.sql.Statement;
import model.Bestelling;

/**
 * Klasse die verbinding maakt met de database en methoden bevat om gegevens te manipuleren en op te
 * halen.
 */
public class DatabaseConnector {
    Statement statement;
    boolean isInitialized;
    
    public void batchInsertion(Bestelling bestelling) throws SQLException, DatabaseException {


    if(!isInitialized)
    throw new DatabaseException("Geen verbinding met database.");


    statement.addBatch(database.BestellingSQL.generateBestellingInsertionCode(bestelling));


    statement.executeBatch();

} 
    public void batchUpdate(int bestellingId, int artikelId, String artikelNaam, int artikelAantal, double artikelPrijs) throws SQLException, DatabaseException{
       if(!isInitialized)
       throw new DatabaseException("Geen verbinding met database.");


        statement.addBatch(database.BestellingSQL.generateArtikelUpdateCode(bestellingId, artikelId, artikelNaam, artikelAantal, artikelPrijs));


        statement.executeBatch();
    }
    
}
