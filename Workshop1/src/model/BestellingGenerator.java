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
import java.sql.Connection;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//test

public class BestellingGenerator extends Stage{
	
	private VBox pnBestelling;
	
	public BestellingGenerator(){
		pnBestelling = new VBox();
		Scene scBestelling = new Scene(pnBestelling);
		setScene(scBestelling);
		setTitle("Nieuwe bestelling");
		
		makeGUI();
		
		show();	
	}
	
	private void makeGUI(){
		TextField tfKlantID = new TextField();
		Label lblKlantID= new Label("Klant ID",tfKlantID);
		lblKlantID.setContentDisplay(ContentDisplay.RIGHT);
		Button btVoegToe = new Button("Voeg bestelling toe");
		pnBestelling.getChildren().addAll(lblKlantID, btVoegToe);
				
		//add TextFields for adding Artikelen
		
		btVoegToe.setOnAction(e->{	
			Bestelling bestelling = new Bestelling();
			//voeg alle info toe aan deze bestelling
			
			BestellingSQL.addToDatabase(bestelling);
		});
	}
	
	private HBox makeArtikelGUI(){
		HBox artikelGUI = new HBox();
		//implement
		
		//maybe get this from artikelGenerator
		
		//add textfields for one artikel
		return artikelGUI;
	}

	public static boolean isKlantID(String klantID){
		boolean justDigits = true;
		for (char ch : klantID.toCharArray()){
			if (!Character.isDigit(ch)){
				justDigits = false;
			}
		}
		
		if (!justDigits){
			System.out.println("Alleen cijfers zijn toegestaan als ID");
			//use error popup instead
		}
		
		return justDigits;
	}
}

