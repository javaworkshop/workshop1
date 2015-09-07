package org.workshop1.dao;

public interface Dao extends AutoCloseable {
    public static final String DEFAULT_LOCATION_XML = "data/xml";
    public static final String DEFAULT_LOCATION_JSON = "data/json";
    
    @Override
    public void close() throws DaoException;
}
