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

import javafx.scene.Node;
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
        private TextField tfKlantId;
        private TextField tfArtikel1Id, tfArtikel1Naam, tfArtikel1Aantal, tfArtikel1Prijs;
        private TextField tfArtikel2Id, tfArtikel2Naam, tfArtikel2Aantal, tfArtikel2Prijs;
        private TextField tfArtikel3Id, tfArtikel3Naam, tfArtikel3Aantal, tfArtikel3Prijs;

        
	public BestellingGenerator(){
		pnBestelling = new VBox();
		Scene scBestelling = new Scene(pnBestelling);
		setScene(scBestelling);
		setTitle("Nieuwe bestelling");
		show();	

		makeGUI();
	}
	
	private void makeGUI(){
		tfKlantId = new TextField();
		Label lblKlantId= new Label("Klant ID",tfKlantId);
		lblKlantId.setContentDisplay(ContentDisplay.RIGHT);
                Button btVoegToe = new Button("Voeg bestelling toe");
                pnBestelling.getChildren().addAll(lblKlantId, makeArtikel1GUI(),makeArtikel2GUI(), makeArtikel3GUI(), btVoegToe);

		btVoegToe.setOnAction(e->{	
                  Bestelling bestelling = new Bestelling();
                  processTextFields(bestelling);
                  BestellingSQL.addToDatabase(bestelling);
                  System.out.println(bestelling);
		});
	}
        
        private void processTextFields(Bestelling bestelling){           
            bestelling.setklantId(Integer.parseInt(tfKlantId.getText()));
            
            // do something about textfields being empty
            
            try{
                 ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel1Id.getText()),
                 tfArtikel1Naam.getText(), Integer.parseInt(tfArtikel1Aantal.getText()),
                 Double.parseDouble(tfArtikel1Prijs.getText()));
            }
            catch (NumberFormatException ex) {
                //make popup?
                System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 1, artikel 1 niet toegevoegd");
            }

            try{
                ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel2Id.getText()),
                tfArtikel2Naam.getText(), Integer.parseInt(tfArtikel2Aantal.getText()),
                    Double.parseDouble(tfArtikel2Prijs.getText()));
            }
            catch (NumberFormatException ex){
                //make popup?
                System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 2, artikel 2 niet toegevoegd");
            }
            
            try{
            ArtikelGenerator.processArtikelInfo(bestelling, Integer.parseInt(tfArtikel3Id.getText()),
                 tfArtikel3Naam.getText(), Integer.parseInt(tfArtikel3Aantal.getText()),
                    Double.parseDouble(tfArtikel3Prijs.getText()));
            }
            catch (NumberFormatException ex){
                //make popup?
                System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 3, artikel 3 niet toegevoegd");
            }
        }
        
        public HBox makeArtikel1GUI(){
                tfArtikel1Id = new TextField();
		Label lblArtikel1Id = new Label("Artikel ID",tfArtikel1Id);
                tfArtikel1Naam = new TextField();
		Label lblArtikel1Naam = new Label("Artikel Naam",tfArtikel1Naam);
                tfArtikel1Aantal = new TextField();
		Label lblArtikel1Aantal = new Label("Artikel Aantal",tfArtikel1Aantal);
                tfArtikel1Aantal.setPrefWidth(50);
                tfArtikel1Prijs = new TextField();
		Label lblArtikel1Prijs= new Label("Artikel Prijs",tfArtikel1Prijs);
                tfArtikel1Prijs.setPrefWidth(50);
                
                HBox pnArtikel1Info = new HBox();
                pnArtikel1Info.getChildren().addAll(lblArtikel1Id, lblArtikel1Naam, lblArtikel1Aantal, lblArtikel1Prijs);
                setContentDisplayRight(pnArtikel1Info);
                return pnArtikel1Info;
        }
        
        public HBox makeArtikel2GUI(){
                tfArtikel2Id = new TextField();
		Label lblArtikel2Id = new Label("Artikel ID",tfArtikel2Id);
                tfArtikel2Naam = new TextField();
		Label lblArtikel2Naam = new Label("Artikel Naam",tfArtikel2Naam);
                tfArtikel2Aantal = new TextField();
		Label lblArtikel2Aantal = new Label("Artikel Aantal",tfArtikel2Aantal);
                tfArtikel2Aantal.setPrefWidth(50);
                tfArtikel2Prijs = new TextField();
		Label lblArtikel2Prijs= new Label("Artikel Prijs",tfArtikel2Prijs);
                tfArtikel2Prijs.setPrefWidth(50);
                
                HBox pnArtikel2Info = new HBox();
                pnArtikel2Info.getChildren().addAll(lblArtikel2Id, lblArtikel2Naam, lblArtikel2Aantal, lblArtikel2Prijs);
                setContentDisplayRight(pnArtikel2Info);
                return pnArtikel2Info;
        }
        
        public HBox makeArtikel3GUI(){
                tfArtikel3Id = new TextField();
		Label lblArtikel3Id = new Label("Artikel ID",tfArtikel3Id);
                tfArtikel3Naam = new TextField();
		Label lblArtikel3Naam = new Label("Artikel Naam",tfArtikel3Naam);
                tfArtikel3Aantal = new TextField();
		Label lblArtikel3Aantal = new Label("Artikel Aantal",tfArtikel3Aantal);
                tfArtikel3Aantal.setPrefWidth(50);
                tfArtikel3Prijs = new TextField();
		Label lblArtikel3Prijs= new Label("Artikel Prijs",tfArtikel3Prijs);
                tfArtikel3Prijs.setPrefWidth(50);
 
                HBox pnArtikel3Info = new HBox();
                pnArtikel3Info.getChildren().addAll(lblArtikel3Id, lblArtikel3Naam, lblArtikel3Aantal, lblArtikel3Prijs);
                setContentDisplayRight(pnArtikel3Info);
                return pnArtikel3Info;
        }
        
        public void setContentDisplayRight(HBox labelRow){
            for (Node node : labelRow.getChildren()){
                if (node instanceof Label){
                    Label label = (Label)node;
                    label.setContentDisplay(ContentDisplay.RIGHT);
                }
            }
        }
}

