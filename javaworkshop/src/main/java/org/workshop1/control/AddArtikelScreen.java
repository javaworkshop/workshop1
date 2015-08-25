package org.workshop1.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.workshop1.model.Artikel;

public class AddArtikelScreen extends Stage{
    private VBox pnArtikel;
    private Button btVoegToe;
    private TextField tfBestellingId;
    private TextField tfArtikelId;
    private TextField tfArtikelNaam;
    private TextField tfArtikelAantal;
    private TextField tfArtikelPrijs;
    
    public AddArtikelScreen(){
      	pnArtikel = new VBox();
	Scene scArtikel = new Scene(pnArtikel);
	setScene(scArtikel);
	setTitle("Voeg artikel toe");
		
	makeGUI();
    }

    private void makeGUI(){
	tfBestellingId = new TextField();
	Label lblBestellingId = new Label("Bestelling ID",tfBestellingId);
	lblBestellingId.setContentDisplay(ContentDisplay.RIGHT);
                
        HBox pnArtikelInfo = new HBox();
        tfArtikelId = new TextField();
	Label lblArtikelId = new Label("Artikel ID",tfArtikelId);
        lblArtikelId.setContentDisplay(ContentDisplay.RIGHT);
                
        tfArtikelNaam = new TextField();
	Label lblArtikelNaam = new Label("Artikel Naam",tfArtikelNaam);
	lblArtikelNaam.setContentDisplay(ContentDisplay.RIGHT);
                
        tfArtikelAantal = new TextField();
	Label lblArtikelAantal = new Label("Artikel Aantal",tfArtikelAantal);
        tfArtikelAantal.setPrefWidth(50);
	lblArtikelAantal.setContentDisplay(ContentDisplay.RIGHT);
                
        tfArtikelPrijs = new TextField();
	Label lblArtikelPrijs = new Label("Artikel Prijs",tfArtikelPrijs);
        tfArtikelPrijs.setPrefWidth(50);
	lblArtikelPrijs.setContentDisplay(ContentDisplay.RIGHT);
                
        pnArtikelInfo.getChildren().addAll(lblArtikelId, lblArtikelNaam, lblArtikelAantal, 
                lblArtikelPrijs);
                
	btVoegToe = new Button("Voeg artikel toe");
        Button btCancel = new Button("Annuleren");
        btCancel.setOnAction(e -> {
            hide();
        });
	pnArtikel.getChildren().addAll(lblBestellingId, pnArtikelInfo, btVoegToe, btCancel);		
    }
        
    public void setVoegToeHandler(EventHandler<ActionEvent> eh) {
        btVoegToe.setOnAction(eh);        
    } 
        
    public void processArtikelInfo(Artikel a){
        a.setBestelling_id(Integer.parseInt(tfBestellingId.getText()));
        a.setArtikel_id(Integer.parseInt(tfArtikelId.getText()));
        a.setArtikel_naam(tfArtikelNaam.getText());
        a.setArtikel_aantal(Integer.parseInt(tfArtikelAantal.getText()));
        a.setArtikel_prijs(Double.parseDouble(tfArtikelPrijs.getText()));
    }
}
