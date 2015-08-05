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
            throw new DatabaseException("Failed to load driver.", ex);
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
    
    /**
     * Executes the given command. Results are stored in rowSet if the command is a query.
     * @param command               the command to be executed
     * @throws SQLException         
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public void executeCommand(String command) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Not connected to database.");

        rowSet.setCommand(command);
        rowSet.execute();
    }
    
    // Dit moet eigenlijk een methode zijn die een klant object als parameter krijgt aangeleverd.
    // Dit klant object kan dan worden gebruikt om sql code te genereren die aan de batch kan worden
    // toegevoegd.
    /**
     * Adds the given command to the current list of commands for the statement object returned by 
     * rowSet.
     * @param command               the command to be  added
     * @throws SQLException 
     * @throws DatabaseException    thrown if database connection has not been initialized yet
     */
    public void addBatch(String command) throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Not connected to database.");
        
        statement.addBatch(command);
    }
    
    /**
     * Empties the current list of commands of rowSet's statement object.
     * @throws SQLException 
     * @throws DatabaseException thrown if database connection has not been initialized yet
     */
    public void clearBatch() throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Not connected to database.");
        
        statement.clearBatch();       
    }
    
    /**
     * Executes the batch of commands previously added to the rowSet's statement object using the
     * addBatch(String command) method.
     * @throws SQLException 
     * @throws DatabaseException thrown if database connection has not been initialized yet
     */
    public void executeBatch() throws SQLException, DatabaseException {
        if(!isInitialized)
            throw new DatabaseException("Not connected to database.");
        
        statement.executeBatch();
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
        if(!isInitialized)
            throw new DatabaseException("Not connected to database.");
        
        return new ArrayList<Klant>();
    }
    
    /**
     * Retrieves the names of all the columns in rowSet, which contains the results from the last
     * executed query.    
     * @return              array of String containing the names of all columns in rowSet
     * @throws SQLException 
     */
    public String[] getCurrentColumnNames() throws SQLException {
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
    public String getCurrentColumnName(int column) throws SQLException {
        return rowSet.getMetaData().getColumnName(column);
    }
    
    /**
     * Retrieves the number of columns in rowSet, which contains the results from the last executed
     * query. 
     * @return              the number of columns of the current query result
     * @throws SQLException  
     */
    public int getCurrentColumnCount() throws SQLException {
        return rowSet.getMetaData().getColumnCount();
    }
    
    /**
     * Retrieves String result from the currently selected row in rowSet, which contains the results
     * from the last executed query. 
     * @param column        the column from which the result should be obtained
     * @return              the String at the specified column in the currently selected row
     * @throws SQLException
     */
    public String getCurrentString(int column) throws SQLException {
        return rowSet.getString(column);
    }
    
    /**
     * Retrieves integer result from the currently selected row in rowSet, which contains the 
     * results from the last executed query. 
     * @param column        the column from which the result should be obtained
     * @return              the integer at the specified column in the currently selected row
     * @throws SQLException
     */
    public int getCurrentInt(int column) throws SQLException {
        return rowSet.getInt(column);
    }
    
    /**
     * Retrieves double result from the currently selected row in rowSet, which contains the results
     * from the last executed query. 
     * @param column        the column from which the result should be obtained
     * @return              the double value at the specified column in the currently selected row
     * @throws SQLException
     */
    public double getCurrentDouble(int column) throws SQLException {
        return rowSet.getDouble(column);
    }
    
    /**
     * Moves rowSet cursor to the next row
     * @return              true if new current row is valid, false if there are no more rows
     * @throws SQLException
     */
    public boolean nextRow() throws SQLException {
        return rowSet.next();       
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
