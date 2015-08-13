package database;

import model.*;
import java.sql.*;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.util.ArrayList;
//import com.sun.rowset.*;

/**
 * Class that establishes and maintains a connection with the database and through which all sql
 * operations are processed.
 */
public class DatabaseConnector {
    private JdbcRowSet rowSet;
    Statement statement;
    
    private boolean isInitialized;
    
    private String driver;
    private String url;
    private String username;
    private String password;
    
    public DatabaseConnector() {
        rowSet = null;
        isInitialized = false;
    }
    
    /**
     * Initializes connection to database. Before this method is run driver, url, username, and 
     * password should be set using the appropriate setter methods.
     * @throws SQLException
     * @throws DatabaseException thrown if driver could not be loaded
     */
    public void connectToDatabase() throws SQLException, DatabaseException {
        try {
            Class.forName(driver);
        }
        catch(Exception ex) {
            throw new DatabaseException("Driver laden mislukt.", ex);
        }
        RowSetFactory rowSetFactory = RowSetProvider.newFactory("com.sun.rowset.RowSetFactoryImpl",
                null);
        rowSet = rowSetFactory.createJdbcRowSet();
        rowSet.setUrl(url);
        rowSet.setUsername(username);
        rowSet.setPassword(password);
        
        statement = DriverManager.getConnection(url, username, password).createStatement();
        //Alternatief:
        //rowSet.setCommand("SHOW TABLES");
        //rowSet.execute();
        //statement = rowSet.getStatement();
        
        isInitialized = true;
    }
    
    /**
     * Executes the given query. The contents of the resulting ResultSet are transferred to a 
     * QueryResult object, which is returned to the caller.
     * @param query                 the query to be executed
     * @return                      a QueryResult object containining query results
     * @throws SQLException
     * @throws DatabaseException     thrown if database connection has not been initialized yet
     */
    public QueryResult executeQuery(String query) throws SQLException, 
            DatabaseException {        
        executeCommand(query);
        return createQueryResult();
    }    
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    private QueryResult createQueryResult() throws SQLException {
        String[] columnNames = getCurrentColumnNames();        
        QueryResult queryResult = new QueryResult();
        
        while(rowSet.next()) {
            QueryResultRow row = new QueryResultRow();
            for(int i = 1; i <= getCurrentColumnCount(); i++) {               
                switch (columnNames[i-1]) {
                     // klant kolommen
                    case "klant_id":
                        row.getKlant().setKlant_id(rowSet.getInt(i));
                        row.getBestelling().setKlant_id(rowSet.getInt(i));
                        queryResult.addColumnName("klant_id");
                        break;
                    case "voornaam":
                        row.getKlant().setVoornaam(rowSet.getString(i));
                        queryResult.addColumnName("voornaam");
                        break;
                    case "tussenvoegsel":
                        row.getKlant().setTussenvoegsel(rowSet.getString(i));
                        queryResult.addColumnName("tussenvoegsel");
                        break;
                    case "achternaam":
                        row.getKlant().setAchternaam(rowSet.getString(i));
                        queryResult.addColumnName("achternaam");
                        break;
                    case "email":
                        row.getKlant().setEmail(rowSet.getString(i));
                        queryResult.addColumnName("email");
                        break;
                    case "straatnaam":
                        row.getKlant().setStraatnaam(rowSet.getString(i));
                        queryResult.addColumnName("straatnaam");
                        break;
                    case "huisnummer":
                        row.getKlant().setHuisnummer(rowSet.getInt(i));
                        queryResult.addColumnName("huisnummer");
                        break;
                    case "toevoeging":
                        row.getKlant().setToevoeging(rowSet.getString(i));
                        queryResult.addColumnName("toevoeging");
                        break;
                    case "postcode":
                        row.getKlant().setPostcode(rowSet.getString(i));
                        queryResult.addColumnName("postcode");
                        break;
                    case "woonplaats":
                        row.getKlant().setWoonplaats(rowSet.getString(i));
                        queryResult.addColumnName("woonplaats");
                        break;
                    // bestelling kolommen
                    case "bestelling_id":
                        row.getBestelling().setBestelling_id(rowSet.getInt(i));
                        queryResult.addColumnName("bestelling_id");
                        break;
                    case "artikel_id1":
                        row.getBestelling().setArtikel_id1(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_id1");
                        break;
                    case "artikel_id2":
                        row.getBestelling().setArtikel_id2(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_id2");
                        break;
                    case "artikel_id3":
                        row.getBestelling().setArtikel_id3(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_id3");
                        break;
                    case "artikel_naam1":
                        row.getBestelling().setArtikel_naam1(rowSet.getString(i));
                        queryResult.addColumnName("artikel_naam1");
                        break;
                    case "artikel_naam2":
                        row.getBestelling().setArtikel_naam2(rowSet.getString(i));
                        queryResult.addColumnName("artikel_naam2");
                        break;
                    case "artikel_naam3":
                        row.getBestelling().setArtikel_naam3(rowSet.getString(i));
                        queryResult.addColumnName("artikel_naam3");
                        break;
                    case "artikel_aantal1":
                        row.getBestelling().setArtikel_aantal1(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_aantal1");
                        break;
                    case "artikel_aantal2":
                        row.getBestelling().setArtikel_aantal2(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_aantal2");
                        break;
                    case "artikel_aantal3":
                        row.getBestelling().setArtikel_aantal3(rowSet.getInt(i));
                        queryResult.addColumnName("artikel_aantal3");
                        break;
                    case "artikel_prijs1":
                        row.getBestelling().setArtikel_prijs1(rowSet.getDouble(i));
                        queryResult.addColumnName("artikel_prijs1");
                        break;
                    case "artikel_prijs2":
                        row.getBestelling().setArtikel_prijs2(rowSet.getDouble(i));
                        queryResult.addColumnName("artikel_prijs2");
                        break;
                    case "artikel_prijs3":
                        row.getBestelling().setArtikel_prijs3(rowSet.getDouble(i));
                        queryResult.addColumnName("artikel_prijs3");
                        break;
                }
            }
            queryResult.addRow(row);
        }
        
        return queryResult;
    }
    
    /**
     * Executes the given command. Results are stored in rowSet if the command is a query.
     * @param command               the command to be executed
     * @throws SQLException         
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public void executeCommand(String command) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Geen verbinding met database.");

        rowSet.setCommand(command);
        rowSet.execute();
    }
    
    /**
     * Inserts an array of klanten into the database using a batch statement.
     * @param klanten               the array of klanten to be inserted
     * @throws SQLException
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public void batchInsertion(Klant[] klanten) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Geen verbinding met database.");
       
        for(Klant klant : klanten)
            statement.addBatch(SqlCodeGenerator.generateKlantInsertionCode(klant));
        statement.executeBatch();
    }    
    
    /**
     * Reads all customer data currently present in the database and stores them in an arraylist
     * object.
     * @return                      an arraylist object containing klant objects for all customers 
     *                              stored in the database
     * @throws SQLException
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public ArrayList<Klant> readAll() throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Geen verbinding met database.");
        
        executeCommand("SELECT * FROM klant");
        ArrayList<Klant> klanten = new ArrayList<>();
        
        while(rowSet.next())
            klanten.add(retrieveKlant());
        
        return klanten;
    }
    
    /**
     * Returns the klant stored in the database with the given klant id. Klant data is returned in a
     * klant object.
     * @param klant_id              the id of the klant that is to be retrieved
     * @return                      the klant with the given id
     * @throws SQLException
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public Klant read(int klant_id) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Geen verbinding met database.");
        
        executeCommand("SELECT * FROM klant WHERE klant_id = " + klant_id);
        rowSet.next();
        Klant klant = retrieveKlant();
        
        return klant;
    }
    
    /**
     * Returns all klanten stored in the database of whom the attribute values correspond to 
     * attribute values of the given klant object. Only the attributes that have been specifically
     * set in the klant object are used for the query. Klanten are returned in an arraylist object.
     * @param k                     the klant object that contains query conditions
     * @return                      klanten that have the same attribute values as the given klant
     * @throws SQLException
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public ArrayList<Klant> read(Klant k) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Geen verbinding met database.");
        
        String sqlcode = SqlCodeGenerator.generateKlantSelectionCode(k);
        executeCommand(sqlcode);
        ArrayList<Klant> klanten = new ArrayList<>();
        
        while(rowSet.next())
            klanten.add(retrieveKlant());
        
        return klanten;
    }    
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    private Klant retrieveKlant() throws SQLException {
        Klant klant = new Klant();
        klant.setKlant_id(rowSet.getInt(1));
        klant.setVoornaam(rowSet.getString(2));
        klant.setTussenvoegsel(rowSet.getString(3));
        klant.setAchternaam(rowSet.getString(4));
        klant.setEmail(rowSet.getString(5));
        klant.setStraatnaam(rowSet.getString(6));
        klant.setHuisnummer(rowSet.getInt(7));
        klant.setToevoeging(rowSet.getString(8));
        klant.setPostcode(rowSet.getString(9));
        klant.setWoonplaats(rowSet.getString(10));
        
        return klant;
    }
    
    /**
     * Retrieves the names of all the columns in rowSet, which contains the results from the last
     * executed query.    
     * @return              array of Strings containing the names of all columns in rowSet
     * @throws SQLException 
     */
    private String[] getCurrentColumnNames() throws SQLException {
        String[] columnNames = new String[getCurrentColumnCount()];
        for(int i = 0; i < columnNames.length; i ++)
            columnNames[i] = getCurrentColumnName(i + 1);
        return columnNames;
    }
    
    /**
     * Retrieves the name of the specified column in rowSet, which contains the results from the
     * last executed query. 
     * @param column        the column from which the name should be returned
     * @return              the name of the specified column
     * @throws SQLException 
     */
    private String getCurrentColumnName(int column) throws SQLException {
        return rowSet.getMetaData().getColumnName(column);
    }
    
    /**
     * Retrieves the number of columns in rowSet, which contains the results from the last executed
     * query. 
     * @return              the number of columns of the current query result
     * @throws SQLException  
     */
    private int getCurrentColumnCount() throws SQLException {
        return rowSet.getMetaData().getColumnCount();
    }    
    
    /**
     * 
     * @param driver 
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    /**
     * 
     * @return 
     */
    public String getDriver() {
        return driver;
    }

    /**
     * 
     * @return 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isInitialized() {
        return isInitialized;
    }
    
}
