/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import model.Bestelling;

/**
 *
 * @author gerbrich2
 */

//test



public class BestellingSQL {
	
        
        private BestellingSQL(){}
        
        public static String generateBestellingInsertionCode(Bestelling bestelling){
            //implement 
            String sqlString = 
                    "Insert into bestelling (klant_id,  artikel_id1,  artikel_id2,  artikel_id3,  artikel_naam1,  artikel_naam2,  artikel_naam3,  artikel_aantal1,  artikel_aantal2,  artikel_aantal3,  artikel_prijs1,  artikel_prijs2,  artikel_prijs3) values (" +
                bestelling.getklantId() + ", " + bestelling.getArtikelId1() + ", " + bestelling.getArtikelId2() + ", " +bestelling.getArtikelId3()
                    + ", \'" + bestelling.getArtikelNaam1() + "\', \'" + bestelling.getArtikelNaam2() + "\', \'" + bestelling.getArtikelNaam3() + "\', " +
                bestelling.getArtikelAantal1() + ", " + bestelling.getArtikelAantal2() + ", " + bestelling.getArtikelAantal3() + ", " +
                bestelling.getArtikelPrijs1() + ", " + bestelling.getArtikelPrijs2() + ", " + bestelling.getArtikelPrijs3() + ")";
            
            
            return sqlString;
        }
        
        public static String generateArtikelUpdateCode(int bestellingId, int artikelId, String artikelNaam, int artikelAantal, double artikelPrijs){
            String sqlString =   "  UPDATE   bestelling SET "
            //artikel id
            +  "artikel_id1 = ( CASE  WHEN (artikel_id1 is null and  IFNULL(artikel_id2,0) <> " + artikelId + "  and IFNULL(artikel_id3,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id1 END )," + 
            " artikel_id2 = (CASE  WHEN (artikel_id2 is null and IFNULL(artikel_id1,0) <> " + artikelId +  " and IFNULL(artikel_id3,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id2 END ), " + 
            "artikel_id3 =  (CASE  WHEN  (artikel_id3 is null and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId + 
            ")    THEN  " + artikelId + " ELSE artikel_id3  END ),"

            //artikel aantal
            + "artikel_aantal1 =  (CASE WHEN (artikel_id1 = " + artikelId +
            ")  	THEN (IFNULL(artikel_aantal1, 0) + " + artikelAantal + ") ELSE artikel_aantal1 END)," + 
            "artikel_aantal2 =(CASE WHEN (artikel_id2 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId +
            ")  	THEN (IFNULL(artikel_aantal2, 0) + " + artikelAantal + ") ELSE artikel_aantal2 END), " + 
            " artikel_aantal3 =  (CASE WHEN (artikel_id3 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId +
            ") 	THEN (IFNULL(artikel_aantal3, 0) + " + artikelAantal + ") ELSE artikel_aantal3 END),"

            //artikelnaam

            + " artikel_naam1 =  (CASE WHEN (artikel_id1 = " + artikelId + 
            ")  THEN \' " + artikelNaam + "\' ELSE artikel_naam1 END)," + 
            "artikel_naam2 =(CASE WHEN (artikel_id2 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId + 
            ")  THEN \' " + artikelNaam + "\' ELSE artikel_naam2 END)," + 
            "artikel_naam3 =  (CASE WHEN (artikel_id3 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId +
             ") THEN \' " + artikelNaam + "\' ELSE artikel_naam3 END),"

            //artikel prijs

            + "artikel_prijs1 =  (CASE WHEN (artikel_id1 = " + artikelId + 
            ")  THEN " + artikelPrijs + " ELSE artikel_prijs1 END)," +
            "artikel_prijs2 =( CASE WHEN (artikel_id2 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId + 
            ")  THEN " + artikelPrijs + " ELSE artikel_prijs2 END)," +
            "artikel_prijs3 =  (CASE WHEN (artikel_id3 = " + artikelId + " and IFNULL(artikel_id1,0) <> " + artikelId + " and IFNULL(artikel_id2,0) <> " + artikelId + 
            ")  THEN " + artikelPrijs + "  ELSE artikel_prijs3 END)" +
              "  WHERE  bestelling_id = " + bestellingId;
            
            return sqlString;
        }
        

        
}

