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
		Label lblBestellingId= new Label("Bestelling ID",tfBestellingId);
		lblBestellingId.setContentDisplay(ContentDisplay.RIGHT);
                
                HBox pnArtikelInfo = new HBox();
                TextField tfArtikelId = new TextField();
		Label lblArtikelId= new Label("Artikel ID",tfArtikelId);
		lblArtikelId.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelNaam = new TextField();
		Label lblArtikelNaam= new Label("Artikel Naam",tfArtikelNaam);
		lblArtikelNaam.setContentDisplay(ContentDisplay.RIGHT);
                
                TextField tfArtikelAantal = new TextField();
		Label lblArtikelAantal= new Label("Artikel Aantal",tfArtikelAantal);
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
			Bestelling bestelling = BestellingSQL.getBestelling(Integer.parseInt(tfBestellingId.getText()));
                        int artikelId = Integer.parseInt(tfArtikelId.getText());
                        String artikelNaam = tfArtikelNaam.getText();
                        int artikelAantal = Integer.parseInt(tfArtikelAantal.getText());
                        double artikelPrijs = Double.parseDouble(tfArtikelPrijs.getText());
                        
                        processArtikelInfo(bestelling, artikelId, artikelNaam, artikelAantal, artikelPrijs);
                        
                        BestellingSQL.updateBestelling(bestelling);
		});
	}
        
        public void processArtikelInfo(Bestelling bestelling, int artikelId, String artikelNaam, int artikelAantal, double artikelPrijs){
                  
                        if (getSameArtikelSlotNr(artikelId, bestelling) == 1){
                            bestelling.setArtikelAantal1(bestelling.getArtikelAantal1()+artikelAantal);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == 2){
                            bestelling.setArtikelAantal2(bestelling.getArtikelAantal2()+artikelAantal);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == 3){
                            bestelling.setArtikelAantal3(bestelling.getArtikelAantal3()+artikelAantal);
                        }
                        else if (getSameArtikelSlotNr(artikelId, bestelling) == -999){
                            if (getEmptySlotNr(bestelling) == 1){
                                bestelling.setArtikelId1(artikelId);
                                bestelling.setArtikelNaam1(artikelNaam);
                                bestelling.setArtikelAantal1(artikelAantal);
                                bestelling.setArtikelPrijs1(artikelPrijs);
                            }
                            else if (getEmptySlotNr(bestelling) == 2){
                                bestelling.setArtikelId2(artikelId);
                                bestelling.setArtikelNaam2(artikelNaam);
                                bestelling.setArtikelAantal2(artikelAantal);
                                bestelling.setArtikelPrijs2(artikelPrijs);

                            }
                            else if (getEmptySlotNr(bestelling) == 3){
                                bestelling.setArtikelId3(artikelId);
                                bestelling.setArtikelNaam3(artikelNaam);
                                bestelling.setArtikelAantal3(artikelAantal);
                                bestelling.setArtikelPrijs3(artikelPrijs);

                            }
                            else if (getEmptySlotNr(bestelling) == -999){
                                //geef foutmelding artikel kan niet worden toegevoegd
                            }
                        }
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
