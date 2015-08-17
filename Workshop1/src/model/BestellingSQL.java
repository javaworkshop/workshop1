/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gerbrich2
 */

//test



public class BestellingSQL {
	
        
        private BestellingSQL(){
            
        }
        
        static String generateBestellingInsertionCode(Bestelling bestelling){
            //implement 
            String s = 
                    "Insert into bestelling (klant_id,  artikel_id1,  artikel_id2,  artikel_id3,  artikel_naam1,  artikel_naam2,  artikel_naam3,  artikel_aantal1,  artikel_aantal2,  artikel_aantal3,  artikel_prijs1,  artikel_prijs2,  artikel_prijs3) values (" +
                bestelling.getklantId() + ", " + bestelling.getArtikelId1() + ", " + bestelling.getArtikelId2() + ", " +bestelling.getArtikelId3()
                    + ", \'" + bestelling.getArtikelNaam1() + "\', \'" + bestelling.getArtikelNaam2() + "\', \'" + bestelling.getArtikelNaam3() + "\', " +
                bestelling.getArtikelAantal1() + ", " + bestelling.getArtikelAantal2() + ", " + bestelling.getArtikelAantal3() + ", " +
                bestelling.getArtikelPrijs1() + ", " + bestelling.getArtikelPrijs2() + ", " + bestelling.getArtikelPrijs3() + ")";
            
            
            return s;
        }
        
	public static void addToDatabase(Bestelling bestelling){
            
            //roep een methode aan die iets kan met de sql
            
	generateBestellingInsertionCode(bestelling);
                    
	}
        
        public static void updateBestelling(Bestelling bestelling){
            //implement
        }
        
        public static void deleteBestelling(Bestelling bestelling){
            //implement
        }
        
        public static Bestelling getBestelling(int bestellingId){
            //implement
            //returns a bestelling object based on info from the database
            return new Bestelling();
        }
        
}

