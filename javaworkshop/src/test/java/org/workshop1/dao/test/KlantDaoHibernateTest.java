package org.workshop1.dao.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.workshop1.dao.BestellingDao;
import org.workshop1.dao.DaoFactory;
import org.workshop1.dao.KlantDao;
import org.workshop1.database.SessionManager;
import org.workshop1.model.Adres;
import org.workshop1.model.Bestelling;
import org.workshop1.model.Klant;

public class KlantDaoHibernateTest {
    private static String mySqlUrl = "jdbc:Mysql://localhost:3306/mydb";
    private static String mySqlDriver = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "qwerty";
    
    private static Klant k1;
    private static Klant k2;
    private static Bestelling b1;
    private static Bestelling b2;
    private static Bestelling b3;
    
    @BeforeClass
    public static void setUp() {       
        Configuration cfg = new Configuration().configure();
        /*cfg     .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", mySqlDriver) // Misschien moet dit de driver zijn en niet de datasource
                .setProperty("hibernate.connection.url", mySqlUrl)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.connection.provider_class", 
                        "com.zaxxer.hikari.hibernate.HikariConnectionProvider") // Opties: com.zaxxer.hikari.hibernate.HikariConnectionProvider, org.hibernate.hikaricp.internal.HikariCPConnectionProvider
                .setProperty("show_sql", "true")
                .setProperty("format_sql", "true");
        cfg.addAnnotatedClass(Klant.class);
        cfg.addAnnotatedClass(Adres.class);
        cfg.addAnnotatedClass(Bestelling.class);*/
        SessionManager.initialize(cfg);
        initData();
    }
    
    private static void initData() {
        // initialize klant k1
        k1 = new Klant();
        //k1.setKlant_id(1);
        k1.setVoornaam("Henk");
        k1.setAchternaam("Man");
        k1.setWoonplaats("De Plaats");
        k1.setStraatnaam("De Straat");
        k1.setHuisnummer(45);
        k1.setPostcode("1234AB");
        k1.setEmail("hm85@hotmail.com");
        
        // initialize klant k2
        k2 = new Klant();
        //k2.setKlant_id(2);
        k2.setVoornaam("Piet");
        k2.setAchternaam("Prutser");
        k2.setWoonplaats("Het Dorp");
        k2.setStraatnaam("Straat in het Dorp");
        k2.setHuisnummer(22);
        k2.setPostcode("4567DH");
        k2.setEmail("pprutser73@gmail.com");
        
        // initialize bestelling b1
        b1 = new Bestelling();
        //b1.setBestelling_id(1);
        b1.setArtikel_aantal1(3);
        b1.setArtikel_id1(456);
        b1.setArtikel_naam1("lamp");
        b1.setArtikel_prijs1(1.50);
        b1.setKlant(k1);
        
        // initialize bestelling b2
        b2 = new Bestelling();
        //b2.setBestelling_id(2);
        b2.setArtikel_aantal1(1);
        b2.setArtikel_id1(23);
        b2.setArtikel_naam1("deur");
        b2.setArtikel_prijs1(32.25);
        b2.setKlant(k1);
        
        // initialize bestelling b3
        b3 = new Bestelling();
        //b3.setBestelling_id(3);
        b3.setArtikel_aantal1(100);
        b3.setArtikel_id1(689);
        b3.setArtikel_naam1("neusdruppels");
        b3.setArtikel_prijs1(5.95);
        b3.setKlant(k2);
    }
    
    @Test
    public void testAdd() {
        KlantDao kDao = DaoFactory.getKlantDao(DaoFactory.HIBERNATE);
        BestellingDao bDao = DaoFactory.getBestellingDao(DaoFactory.HIBERNATE);
        kDao.add(k1);
        kDao.add(k2);
        bDao.add(b1);
        bDao.add(b2);
        bDao.add(b3);
        
        try (Session session = SessionManager.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Klant klant1 = (Klant) session.byId(Klant.class).load(k1.getKlant_id());
            Klant klant2 = (Klant) session.byId(Klant.class).load(k2.getKlant_id());
            Bestelling bestelling1 = (Bestelling) session.byId(Bestelling.class)
                    .load(b1.getBestelling_id());
            Bestelling bestelling2 = (Bestelling) session.byId(Bestelling.class)
                    .load(b2.getBestelling_id());
            Bestelling bestelling3 = (Bestelling) session.byId(Bestelling.class)
                    .load(b3.getBestelling_id());
            
            k1.setKlant_id(1);
            k2.setKlant_id(2);
            b1.setBestelling_id(1);
            b2.setBestelling_id(2);
            b3.setBestelling_id(3);
            assertEquals(k1, klant1);
            assertEquals(k2, klant2);
            assertEquals(b1, bestelling1);
            assertEquals(b2, bestelling2);
            assertEquals(b3, bestelling3);
        }
    }
}
