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
                HBox pnArtikel1Info = new HBox();
                TextField tfArtikel1Id = new TextField();
		Label lblArtikel1Id = new Label("Artikel ID",tfArtikel1Id);
		lblArtikel1Id.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel1Naam = new TextField();
		Label lblArtikel1Naam = new Label("Artikel Naam",tfArtikel1Naam);
		lblArtikel1Naam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel1Aantal = new TextField();
		Label lblArtikel1Aantal = new Label("Artikel Aantal",tfArtikel1Aantal);
                tfArtikel1Aantal.setPrefWidth(50);
		lblArtikel1Aantal.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel1Prijs = new TextField();
		Label lblArtikel1Prijs= new Label("Artikel Prijs",tfArtikel1Prijs);
                tfArtikel1Prijs.setPrefWidth(50);
		lblArtikel1Prijs.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikel1Info.getChildren().addAll(lblArtikel1Id, lblArtikel1Naam, lblArtikel1Aantal, lblArtikel1Prijs);
                
                //info artikel 2
                HBox pnArtikel2Info = new HBox();
                TextField tfArtikel2Id = new TextField();
		Label lblArtikel2Id = new Label("Artikel ID",tfArtikel2Id);
		lblArtikel2Id.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel2Naam = new TextField();
		Label lblArtikel2Naam = new Label("Artikel Naam",tfArtikel2Naam);
		lblArtikel2Naam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel2Aantal = new TextField();
		Label lblArtikel2Aantal = new Label("Artikel Aantal",tfArtikel2Aantal);
                tfArtikel2Aantal.setPrefWidth(50);
		lblArtikel2Aantal.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel2Prijs = new TextField();
		Label lblArtikel2Prijs= new Label("Artikel Prijs",tfArtikel2Prijs);
                tfArtikel2Prijs.setPrefWidth(50);
		lblArtikel2Prijs.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikel2Info.getChildren().addAll(lblArtikel2Id, lblArtikel2Naam, lblArtikel2Aantal, lblArtikel2Prijs);

                //info artikel 3
                HBox pnArtikel3Info = new HBox();
                TextField tfArtikel3Id = new TextField();
		Label lblArtikel3Id = new Label("Artikel ID",tfArtikel3Id);
		lblArtikel3Id.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel3Naam = new TextField();
		Label lblArtikel3Naam = new Label("Artikel Naam",tfArtikel3Naam);
		lblArtikel3Naam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel3Aantal = new TextField();
		Label lblArtikel3Aantal = new Label("Artikel Aantal",tfArtikel3Aantal);
                tfArtikel3Aantal.setPrefWidth(50);
		lblArtikel3Aantal.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikel3Prijs = new TextField();
		Label lblArtikel3Prijs= new Label("Artikel Prijs",tfArtikel3Prijs);
                tfArtikel3Prijs.setPrefWidth(50);
		lblArtikel3Prijs.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikel3Info.getChildren().addAll(lblArtikel3Id, lblArtikel3Naam, lblArtikel3Aantal, lblArtikel3Prijs);

                pnBestelling.getChildren().addAll(lblklantId, pnArtikel1Info,pnArtikel2Info, pnArtikel3Info, btVoegToe);

		btVoegToe.setOnAction(e->{	
                            Bestelling bestelling = new Bestelling();
                        
                            bestelling.setklantId(Integer.parseInt(tfklantId.getText()));

                            ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel1Id.getText()),
                                    tfArtikel1Naam.getText(), Integer.parseInt(tfArtikel1Aantal.getText()),
                                           Double.parseDouble(tfArtikel1Prijs.getText()));

                            ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel2Id.getText()),
                                    tfArtikel2Naam.getText(), Integer.parseInt(tfArtikel2Aantal.getText()),
                                           Double.parseDouble(tfArtikel2Prijs.getText()));
                                              
                            ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel3Id.getText()),
                                        tfArtikel3Naam.getText(), Integer.parseInt(tfArtikel3Aantal.getText()),
                                               Double.parseDouble(tfArtikel3Prijs.getText()));
                                              
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

