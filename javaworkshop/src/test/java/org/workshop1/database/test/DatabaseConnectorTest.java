package org.workshop1.database.test;

import org.workshop1.database.DatabaseConnector;
import org.workshop1.database.DatabaseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.workshop1.model.Artikel;
import org.workshop1.model.Bestelling;
import org.workshop1.model.Klant;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class DatabaseConnectorTest {  
    
    private DatabaseConnector dbConnector;
    private Klant k1;
    private Klant k2;
    private Bestelling b1;
    private Bestelling b2;
    private Bestelling b3;
    private Artikel a1;
    
    private String mySqlUrl = "jdbc:Mysql://localhost:3306/mydb";
    private String mySqlDriver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "qwerty";
    private boolean isDatabaseInitialized = false;
    
    @Before
    public void setUp() throws SQLException, DatabaseException, ClassNotFoundException, 
            FileNotFoundException {
        if(!isDatabaseInitialized) {
            initMySqlDatabase();
            dbConnector = new DatabaseConnector();
            dbConnector.setUrl(mySqlUrl);
            dbConnector.setDataSourceType(DatabaseConnector.HIKARI_CP_DATASOURCE);
            dbConnector.setDatabaseType(DatabaseConnector.MYSQL_DATABASE);
            dbConnector.setUsername(username);
            dbConnector.setPassword(password);
            dbConnector.connectToDatabase();
            initializeData();
            addData();
            isDatabaseInitialized = true;
        }
        else {
            initializeData();
            addData();
        }
        
    } 
    
    private void initMySqlDatabase() throws SQLException, ClassNotFoundException, 
            FileNotFoundException {
        Class.forName(mySqlDriver);
        
        File file = new File("scripts" + File.separator + "KlantDatabase.sql");
        try(Connection con = DriverManager.getConnection(mySqlUrl, username, password);
            Statement statement = con.createStatement()
        ) {
            statement.execute("DROP DATABASE IF EXISTS mydb");
            new ScriptRunner(con).runScript(new BufferedReader(new FileReader(file)));
        }
    }
    
    private void initializeData() {
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
        
        // initialize bestelling b1
        b1 = new Bestelling();
        b1.setKlant_id(1);
        b1.setBestelling_id(1);
        b1.setArtikel_aantal1(3);
        b1.setArtikel_id1(456);
        b1.setArtikel_naam1("lamp");
        b1.setArtikel_prijs1(1.50);
        
        // initialize bestelling b2
        b2 = new Bestelling();
        b2.setKlant_id(1);
        b2.setBestelling_id(2);
        b2.setArtikel_aantal1(1);
        b2.setArtikel_id1(23);
        b2.setArtikel_naam1("deur");
        b2.setArtikel_prijs1(32.25);
        
        // initialize bestelling b3
        b3 = new Bestelling();
        b3.setKlant_id(2);
        b3.setBestelling_id(3);
        b3.setArtikel_aantal1(100);
        b3.setArtikel_id1(689);
        b3.setArtikel_naam1("neusdruppels");
        b3.setArtikel_prijs1(5.95);
        
        // initialize artikel a1
        a1 = new Artikel();
        a1.setArtikel_aantal(4);
        a1.setArtikel_id(43);
        a1.setArtikel_prijs(99.99);
        a1.setArtikel_naam("ding");
        a1.setBestelling_id(2);
    }
    
    private void addData() throws SQLException, DatabaseException {
        dbConnector.addKlant(k1);
        dbConnector.addKlant(k2);
        dbConnector.addBestelling(b1);
        dbConnector.addBestelling(b2);
        dbConnector.addBestelling(b3);
        dbConnector.addArtikel(a1);
        
        // update bestelling b2 with artikel a1 for comparison purposes
        b2.setArtikel_aantal2(4);
        b2.setArtikel_id2(43);
        b2.setArtikel_naam2("ding");
        b2.setArtikel_prijs2(99.99);
    }
    
    @Test
    public void testInitialData() throws SQLException, DatabaseException {
        List kList = dbConnector.readAll();
        assertEquals(k1, kList.get(0));
        assertEquals(k2, kList.get(1));        
        assertEquals(b1, dbConnector.readBestelling(1));
        Bestelling b = dbConnector.readBestelling(1);
        assertEquals(b2, dbConnector.readBestelling(2));
        assertEquals(b3, dbConnector.readBestelling(3));
    }
    
    @Test
    public void testDeleteKlant() throws SQLException, DatabaseException {
        dbConnector.deleteKlant(1);
        List kList = dbConnector.readAll();
        assertTrue(kList.size() == 1);
        assertEquals(k2, kList.get(0));
        System.out.println(kList.get(0));
        assertEquals(null, dbConnector.readBestelling(1));
        assertEquals(null, dbConnector.readBestelling(2));
        assertEquals(b3, dbConnector.readBestelling(3));
    }   
    
    @After
    public void tearDownTest() throws SQLException, DatabaseException {
        dbConnector.clearDatabase();
    }
}
