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
		TextField tfklantId = new TextField();
		Label lblklantId= new Label("Klant ID",tfklantId);
		lblklantId.setContentDisplay(ContentDisplay.RIGHT);
		Button btVoegToe = new Button("Voeg bestelling toe");
                
                //info artikel 1
                HBox pnArtikelInfo = new HBox();
                TextField tfArtikelId = new TextField();
		Label lblArtikelId = new Label("Artikel ID",tfArtikelId);
		lblArtikelId.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelNaam = new TextField();
		Label lblArtikelNaam = new Label("Artikel Naam",tfArtikelNaam);
		lblArtikelNaam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelAantal = new TextField();
		Label lblArtikelAantal = new Label("Artikel Aantal",tfArtikelAantal);
                tfArtikelAantal.setPrefWidth(50);
		lblArtikelAantal.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelPrijs = new TextField();
		Label lblArtikelPrijs= new Label("Artikel Prijs",tfArtikelPrijs);
                tfArtikelPrijs.setPrefWidth(50);
		lblArtikelPrijs.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikelInfo.getChildren().addAll(lblArtikelId, lblArtikelNaam, lblArtikelAantal, lblArtikelPrijs);
		
                
                pnBestelling.getChildren().addAll(lblklantId, pnArtikelInfo, btVoegToe);

		btVoegToe.setOnAction(e->{	
			Bestelling bestelling = new Bestelling();
                        
			//voeg alle info toe aan deze bestelling
                        bestelling.setklantId(Integer.parseInt(tfklantId.getText()));
                        
			int artikelId = Integer.parseInt(tfArtikelId.getText());
                        String artikelNaam = tfArtikelNaam.getText();
                        int artikelAantal = Integer.parseInt(tfArtikelAantal.getText());
                        double artikelPrijs = Double.parseDouble(tfArtikelPrijs.getText());
                        
                        ArtikelGenerator.processArtikelInfo(bestelling, artikelId, artikelNaam, artikelAantal, artikelPrijs);
                        
                        
			BestellingSQL.addToDatabase(bestelling);
		});
	}
	
	private HBox makeArtikelGUI(){
                HBox pnArtikelInfo = new HBox();
                TextField tfArtikelId = new TextField();
		Label lblArtikelId = new Label("Artikel ID",tfArtikelId);
		lblArtikelId.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelNaam = new TextField();
		Label lblArtikelNaam = new Label("Artikel Naam",tfArtikelNaam);
		lblArtikelNaam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelAantal = new TextField();
		Label lblArtikelAantal = new Label("Artikel Aantal",tfArtikelAantal);
                tfArtikelAantal.setPrefWidth(50);
		lblArtikelAantal.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelPrijs = new TextField();
		Label lblArtikelPrijs= new Label("Artikel Prijs",tfArtikelPrijs);
                tfArtikelPrijs.setPrefWidth(50);
		lblArtikelPrijs.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikelInfo.getChildren().addAll(lblArtikelId, lblArtikelNaam, lblArtikelAantal, lblArtikelPrijs);
                
		return pnArtikelInfo;
	}

	public static boolean isklantId(String klantId){
		boolean justDigits = true;
		for (char ch : klantId.toCharArray()){
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

