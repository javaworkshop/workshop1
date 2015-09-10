/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/*
/**
 *
 * @author gerbrich2
 */
public class ArtikelGenerator extends Stage{
    VBox pnArtikel;
    
    public ArtikelGenerator(){
        	pnArtikel = new VBox();
		Scene scArtikel = new Scene(pnArtikel);
		setScene(scArtikel);
		setTitle("Voeg artikel toe");
		
		makeGUI();
		
		show();	
    }

	private void makeGUI(){
		TextField tfBestellingId = new TextField();
		Label lblBestellingId = new Label("Bestelling ID",tfBestellingId);
		lblBestellingId.setContentDisplay(ContentDisplay.RIGHT);
                
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
                
		Button btVoegToe = new Button("Voeg artikel toe");
		pnArtikel.getChildren().addAll(lblBestellingId, pnArtikelInfo, btVoegToe);

		btVoegToe.setOnAction(e->{	
                        int bestellingId = Integer.parseInt(tfBestellingId.getText());
                        int artikelId = Integer.parseInt(tfArtikelId.getText());
                        String artikelNaam = tfArtikelNaam.getText();
                        int artikelAantal = Integer.parseInt(tfArtikelAantal.getText());
                        double artikelPrijs = Double.parseDouble(tfArtikelPrijs.getText());
                        
                        processArtikelInfo(bestellingId, artikelId, artikelNaam, artikelAantal, artikelPrijs);

		});
	}
        
        public static void processArtikelInfo(int bestellingId,int artikelId, String artikelNaam, int artikelAantal, double artikelPrijs){
            //add a referral to a method in another part of the program 
            //that can handle the sql
            control.Controller.getDbConnector().batchUpdate(bestellingId, artikelId, artikelNaam, artikelAantal, artikelPrijs);
        }
       
  
        
}