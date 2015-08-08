package database;

import model.*;
import java.sql.*;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.util.ArrayList;
import com.sun.rowset.*;

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
        //rowSet.setCommand("SHOW COLUMNS FROM klant");
        //rowSet.execute();
        //statement = rowSet.getStatement();
        
        isInitialized = true;
    }
    
    public QueryResult executeQuery(String query) throws SQLException, DatabaseException {
        executeCommand(query);
        QueryResult queryResult = createQueryResult();
        return queryResult;
    }
    
    private QueryResult createQueryResult() throws SQLException {
        ResultSetMetaData rsMeta = rowSet.getMetaData();
        String[] columnNames = getCurrentColumnNames();
        QueryResult queryResult = new QueryResult();        
        
        rowSet.next();
        for(int i = 1; i <= getCurrentColumnCount(); i++) {
            ArrayList<String> cellValues = new ArrayList<>();
            int type = rsMeta.getColumnType(i);
            do {
                if(type == Types.CHAR || type == Types.VARCHAR)
                    cellValues.add(rowSet.getString(i));
                else if(type == Types.INTEGER)
                    cellValues.add(rowSet.getInt(i) + "");
                else if(type == Types.NULL)
                    cellValues.add("");
            }
            while(rowSet.next());
            queryResult.addColumn(columnNames[i - 1], cellValues);
            rowSet.first();
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
            statement.addBatch(generateKlantInsertionCode(klant));
        statement.executeBatch();
    }
    
    /**
     * Generates sql code to insert the given klant into the database.
     * @param klant klant to be inserted
     * @return      sql code to insert klant
     */
    private String generateKlantInsertionCode(Klant klant) {
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
        sqlCode += (klant.getHuisnummer() == 0) ? "NULL, " : klant.getHuisnummer() + ", ";
        sqlCode += (klant.getToevoeging() == null) ? "NULL, " : "'" + 
            klant.getToevoeging() + "', ";    
        sqlCode += (klant.getPostcode() == null) ? "NULL, " : "'" + 
            klant.getPostcode() + "', ";            
        sqlCode += (klant.getWoonplaats() == null) ? "NULL, " : "'" + 
            klant.getWoonplaats() + "')";
        
        return sqlCode;
    }
    
    /**
     * (Unfinished) Reads all customer data currently present in the database and stores them in an
     * arraylist object.
     * @return                      an arraylist object containing klant objects for all customers 
     *                              stored in the database
     * @throws SQLException
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public ArrayList<Klant> readAll() throws SQLException, DatabaseException {
        
        
        return new ArrayList<Klant>();
    }
    
    /**
     * Retrieves the names of all the columns in rowSet, which contains the results from the last
     * executed query.    
     * @return              array of String containing the names of all columns in rowSet
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

    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isInitialized() {
        return isInitialized;
    }
    
}
