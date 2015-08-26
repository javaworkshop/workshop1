package org.workshop1.database;

/**
 * Custom Exception class used by DatabaseConnector. Meant to be used for cases where an Exception 
 * has to be thrown, but where an SQLException is not appropriate.
 */
public class DatabaseException extends Exception {
    
    public DatabaseException() {}
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(Throwable cause) {
        super(cause);
    }
    
    public DatabaseException(String message, Exception ex) {
        super(message, ex);
    } 
    
}
