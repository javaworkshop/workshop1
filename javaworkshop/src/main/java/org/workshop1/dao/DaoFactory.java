package org.workshop1.dao;

import java.sql.Connection;

public class DaoFactory {
    public static final int MY_SQL = 1;
    public static final int FIREBIRD = 2;
    public static final int XML = 3;
    public static final int JSON = 4;
    public static final int HIBERNATE = 5;
    
    public static BestellingDao getBestellingDao(int type) throws DaoConfigurationException {
        if(type == XML)
            return new BestellingDaoXml();
        else if(type == JSON)
            throw new UnsupportedOperationException("Not supported yet.");
        else if(type == HIBERNATE)
            throw new UnsupportedOperationException("Not supported yet.");
        else
            throw new DaoConfigurationException("Ongeldig type");
    }
    
    public static BestellingDao getBestellingDao(int type, Connection con) throws DaoConfigurationException {
        if(type == MY_SQL)
            return new BestellingDaoMySql(con);
        else if(type == FIREBIRD)
            return new BestellingDaoMySql(con);
        else
            return getBestellingDao(type);
    }
    
    public static KlantDao getKlantDao(int type) throws DaoConfigurationException {
        if(type == XML)
            return new KlantDaoXml();
        else if(type == JSON)
            throw new UnsupportedOperationException("Not supported yet.");
        else if(type == HIBERNATE)
            return new KlantDaoHibernate();
        else
            throw new DaoConfigurationException("Ongeldig type");
    }
    
    public static KlantDao getKlantDao(int type, Connection con) throws DaoConfigurationException {
        if(type == MY_SQL)
            return new KlantDaoMySql(con);
        else if(type == FIREBIRD)
            return new KlantDaoMySql(con);
        else
            return getKlantDao(type);
    }
}
