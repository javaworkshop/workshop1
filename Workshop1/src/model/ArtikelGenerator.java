/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		Label lblBestellingId= new Label("Bestelling ID",tfBestellingId);
		lblBestellingId.setContentDisplay(ContentDisplay.RIGHT);
                
                HBox pnArtikelInfo = new HBox();
                TextField tfArtikelId = new TextField();
		Label lblArtikelId= new Label("Artikel ID",tfArtikelId);
		lblArtikelId.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelNaam = new TextField();
		Label lblArtikelNaam= new Label("Artikel Naam",tfArtikelNaam);
		lblArtikelNaam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelAmount = new TextField();
		Label lblArtikelAmount= new Label("Artikel Aantal",tfArtikelAmount);
                tfArtikelAmount.setPrefWidth(50);
		lblArtikelAmount.setContentDisplay(ContentDisplay.RIGHT);
                
                pnArtikelInfo.getChildren().addAll(lblArtikelId, lblArtikelNaam, lblArtikelAmount);
                
		Button btVoegToe = new Button("Voeg artikel toe");
		pnArtikel.getChildren().addAll(lblBestellingId, pnArtikelInfo, btVoegToe);

		btVoegToe.setOnAction(e->{	
			Bestelling bestelling = BestellingSQL.getBestelling(Integer.parseInt(tfBestellingId.getText()));
                        int artikelId = Integer.parseInt(tfArtikelId.getText());
                        
                        if (getSameArtikelSlotNr(artikelId, bestelling) == 1){
                            bestelling.setArtikelAantal1(bestelling.getArtikelAantal1()+1);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == 2){
                            bestelling.setArtikelAantal2(bestelling.getArtikelAantal2()+1);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == 3){
                            bestelling.setArtikelAantal3(bestelling.getArtikelAantal3()+1);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == -999){
                            if (getEmptySlotNr(bestelling) == 1){
                                bestelling.setArtikelId1(Integer.parseInt(tfArtikelId.getText()));
                                bestelling.setArtikelNaam1(tfArtikelNaam.getText());
                                bestelling.setArtikelAantal1(bestelling.getArtikelAantal1()+1);
                            }
                            else if (getEmptySlotNr(bestelling) == 2){
                                bestelling.setArtikelId2(Integer.parseInt(tfArtikelId.getText()));
                                bestelling.setArtikelNaam2(tfArtikelNaam.getText());
                                bestelling.setArtikelAantal2(bestelling.getArtikelAantal2()+1);
                            }
                            else if (getEmptySlotNr(bestelling) == 3){
                                bestelling.setArtikelId3(Integer.parseInt(tfArtikelId.getText()));
                                bestelling.setArtikelNaam3(tfArtikelNaam.getText());
                                bestelling.setArtikelAantal3(bestelling.getArtikelAantal3()+1);
                            }
                            else if (getEmptySlotNr(bestelling) == -999){
                                //geef foutmelding artikel kan niet worden toegevoegd
                            }
                        }
                        
                        BestellingSQL.updateBestelling(bestelling);
		});
	}
        
        public int getSameArtikelSlotNr(int artikelId, Bestelling bestelling){
            if (bestelling.getArtikelId1() == artikelId) return 1;
            else if (bestelling.getArtikelId2() == artikelId) return 2;
            else if (bestelling.getArtikelId3() == artikelId) return 3;
            else return -999;
        }
        
        public int getEmptySlotNr(Bestelling bestelling){
            if (bestelling.getArtikelId1() == 0) return 1;
            else if (bestelling.getArtikelId2() == 0) return 2;
            else if (bestelling.getArtikelId3() == 0) return 3;
            else return -999;
        }
}
