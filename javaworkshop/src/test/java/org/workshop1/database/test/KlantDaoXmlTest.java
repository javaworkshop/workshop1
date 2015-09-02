package org.workshop1.database.test;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workshop1.dao.DaoException;
import org.workshop1.dao.KlantDaoXml;
import org.workshop1.model.Data;
import org.workshop1.model.Klant;

public class KlantDaoXmlTest {
    private final Logger logger = LoggerFactory.getLogger(KlantDaoXml.class);
    private Klant k1;
    private Klant k2;
    private KlantDaoXml klantDao;
    
    @Before
    public void setUp() {
        initData();
        klantDao = new KlantDaoXml();
    }
    
    private void initData() {
        // initialize klant k1
        k1 = new Klant();
        k1.setKlant_id(1);
        k1.setVoornaam("Henk");
        k1.setAchternaam("Man");
        k1.setWoonplaats("De Plaats");
        k1.setStraatnaam("De Straat");
        k1.setHuisnummer(45);
        k1.setPostcode("1234AB");
        k1.setEmail("hm85@hotmail.com");
        
        // initialize klant k2
        k2 = new Klant();
        k2.setKlant_id(2);
        k2.setVoornaam("Piet");
        k2.setAchternaam("Prutser");
        k2.setWoonplaats("Het Dorp");
        k2.setStraatnaam("Straat in het Dorp");
        k2.setHuisnummer(22);
        k2.setPostcode("4567DH");
        k2.setEmail("pprutser73@gmail.com");
    }
    
    @Test
    public void testAdd() throws DaoException {
        klantDao.add(k1);
        klantDao.add(k2);
    }
            
    @Test
    /*public void testRead() {
        ArrayList<Klant> klanten = klantDao.readAll();
        logger.info(klanten.get(0).toString());
        assertEquals(klanten.get(0), k1);
    } */
    
    @After
    public void tearDown() {
        logger.info("test voltooid");
    }
}
