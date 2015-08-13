package database;

/**
 * 
 */
public class DatabaseException extends Exception {
    
    public DatabaseException() {}
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Exception ex) {
        super(message, ex);
    } 
    
}
