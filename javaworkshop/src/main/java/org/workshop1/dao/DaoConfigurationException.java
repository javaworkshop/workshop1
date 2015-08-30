package org.workshop1.dao;

/**
 * This class represents an exception in the Dao configuration which cannot be resolved at runtime,
 * such as a missing resource in the classpath, a missing property in the properties file, etcetera.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class DaoConfigurationException extends RuntimeException {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L; //geen idee wat dit is

    // Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a DaoConfigurationException with the given detail message.
     * @param message The detail message of the DaoConfigurationException.
     */
    public DaoConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a DaoConfigurationException with the given root cause.
     * @param cause The root cause of the DaoConfigurationException.
     */
    public DaoConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DaoConfigurationException with the given detail message and root cause.
     * @param message The detail message of the DaoConfigurationException.
     * @param cause The root cause of the DaoConfigurationException.
     */
    public DaoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}