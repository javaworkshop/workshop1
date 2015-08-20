
package control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bestelling;

public class AddBestellingScreen extends Stage{
	
    private VBox pnBestelling;
    private TextField tfKlantId;
    private TextField tfArtikel1Id, tfArtikel1Naam, tfArtikel1Aantal, tfArtikel1Prijs;
    private TextField tfArtikel2Id, tfArtikel2Naam, tfArtikel2Aantal, tfArtikel2Prijs;
    private TextField tfArtikel3Id, tfArtikel3Naam, tfArtikel3Aantal, tfArtikel3Prijs;
    private Button btVoegToe;
        
    public AddBestellingScreen(){
	pnBestelling = new VBox();
	Scene scBestelling = new Scene(pnBestelling);
	setScene(scBestelling);
	setTitle("Nieuwe bestelling");
        makeGUI();
    }
	
    private void makeGUI(){
        tfKlantId = new TextField();
        Label lblKlantId= new Label("Klant ID",tfKlantId);
        lblKlantId.setContentDisplay(ContentDisplay.RIGHT);
        btVoegToe = new Button("Voeg bestelling toe");
        Button btCancel = new Button("Annuleren");
        btCancel.setOnAction(e -> {
            hide();
        });
        pnBestelling.getChildren().addAll(lblKlantId, makeArtikel1GUI(),makeArtikel2GUI(), 
                makeArtikel3GUI(), btVoegToe, btCancel);       
    }
        
    public void processTextFields(Bestelling bestelling){           
        bestelling.setKlant_id(Integer.parseInt(tfKlantId.getText()));
            
        try{
            processArtikelInfo(bestelling, Integer.parseInt(tfArtikel1Id.getText()),
                    tfArtikel1Naam.getText(), Integer.parseInt(tfArtikel1Aantal.getText()),
                    Double.parseDouble(tfArtikel1Prijs.getText()));
        }
        catch (NumberFormatException ex) {
            //make popup?
            System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 1, artikel 1 niet toegevoegd");
        }

        try{
            processArtikelInfo(bestelling, Integer.parseInt(tfArtikel2Id.getText()),
                    tfArtikel2Naam.getText(), Integer.parseInt(tfArtikel2Aantal.getText()),
                    Double.parseDouble(tfArtikel2Prijs.getText()));
        }
        catch (NumberFormatException ex){
            //make popup?
            System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 2, artikel 2 niet toegevoegd");
        }
            
        try{
            processArtikelInfo(bestelling, Integer.parseInt(tfArtikel3Id.getText()),
                    tfArtikel3Naam.getText(), Integer.parseInt(tfArtikel3Aantal.getText()),
                    Double.parseDouble(tfArtikel3Prijs.getText()));
        }
        catch (NumberFormatException ex){
            //make popup?
            System.out.println("Er zijn niet op de juiste plek cijfers ingevuld bij artikel 3, artikel 3 niet toegevoegd");
        }
    }
        
              
    private void processArtikelInfo(Bestelling bestelling, int artikelId, String artikelNaam, int artikelAantal, double artikelPrijs){
        if (getSameArtikelSlotNr(artikelId, bestelling) == 1){
            bestelling.setArtikel_aantal1(bestelling.getArtikel_aantal1()+artikelAantal);
        }
        else if (getSameArtikelSlotNr(artikelId, bestelling) == 2){
            bestelling.setArtikel_aantal2(bestelling.getArtikel_aantal2()+artikelAantal);
        }
        else if (getSameArtikelSlotNr(artikelId, bestelling) == 3){
            bestelling.setArtikel_aantal3(bestelling.getArtikel_aantal3()+artikelAantal);
        }
        else if (getSameArtikelSlotNr(artikelId, bestelling) == -999){
            if (getEmptySlotNr(bestelling) == 1){
                bestelling.setArtikel_id1(artikelId);
                bestelling.setArtikel_naam1(artikelNaam);
                bestelling.setArtikel_aantal1(artikelAantal);
                bestelling.setArtikel_prijs1(artikelPrijs);
            }
            else if (getEmptySlotNr(bestelling) == 2){
                bestelling.setArtikel_id2(artikelId);
                bestelling.setArtikel_naam2(artikelNaam);
                bestelling.setArtikel_aantal2(artikelAantal);
                bestelling.setArtikel_prijs2(artikelPrijs);
            }
            else if (getEmptySlotNr(bestelling) == 3){
               bestelling.setArtikel_id3(artikelId);
               bestelling.setArtikel_naam3(artikelNaam);
               bestelling.setArtikel_aantal3(artikelAantal);
               bestelling.setArtikel_prijs3(artikelPrijs);
            }
            else if (getEmptySlotNr(bestelling) == -999){
                //geef foutmelding artikel kan niet worden toegevoegd, doe dit met popup
                System.out.println("Er zijn al drie verschillende artikelen, dit artikel kan niet worden toegevoegd.");
            }
        }
    }
        
    private int getSameArtikelSlotNr(int artikelId, Bestelling bestelling){
        if (bestelling.getArtikel_id1() == artikelId) return 1;
        else if (bestelling.getArtikel_id2() == artikelId) return 2;
        else if (bestelling.getArtikel_id3() == artikelId) return 3;
        else return -999;
    }
        
    private int getEmptySlotNr(Bestelling bestelling){
        if (bestelling.getArtikel_id1() == 0) return 1;
        else if (bestelling.getArtikel_id2() == 0) return 2;
        else if (bestelling.getArtikel_id3() == 0) return 3;
        else return -999;
    }
        
    private HBox makeArtikel1GUI(){
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
        
    private HBox makeArtikel2GUI(){
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
        
    private HBox makeArtikel3GUI(){
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
        
    private void setContentDisplayRight(HBox labelRow){
        for (Node node : labelRow.getChildren()){
            if (node instanceof Label){
                Label label = (Label)node;
                label.setContentDisplay(ContentDisplay.RIGHT);
            }
        }
    }
    
    public void setVoegToeHandler(EventHandler<ActionEvent> eh) {
        btVoegToe.setOnAction(eh);        
    } 
}