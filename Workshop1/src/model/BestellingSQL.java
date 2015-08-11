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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BestellingSQL {
	
	static Connection connection; //initialize this

	public static void addToDatabase(Bestelling bestelling){
			try{
				//zorg ervoor dat alle datafields van de bestelling naar de database worden geschreven

				
				String sql = "INSERT INTO Bestelling (klant_id), VALUES (?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, bestelling.getklantId());
				int rowsInserted = statement.executeUpdate();
				if (rowsInserted>0){
					System.out.println("Bestelling toegevoegd");
				}
			}
			catch(SQLException ex){
				//make exception pop up
			}
			catch (Exception ex){
				//make exception pop up
			}
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
        /*
}

