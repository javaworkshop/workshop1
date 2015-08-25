package org.workshop1.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.workshop1.model.Klant;
import org.apache.commons.validator.GenericValidator;

public class AddKlantScreen extends Stage {

    TextField tfVoornaam;
    TextField tfAchternaam;
    TextField tfTussenvoegsel;
    TextField tfEmail;
    TextField tfStraatnaam;
    TextField tfPostcode;
    TextField tfToevoeging;
    TextField tfHuisnummer;
    TextField tfWoonplaats;
    Button btVoegToe;

    GridPane gridPane;
    Stage stMaakNieuweKlant;
    Scene scene;

    public AddKlantScreen() {

        tfVoornaam = new TextField();
        tfAchternaam = new TextField();
        tfTussenvoegsel = new TextField();
        tfEmail = new TextField();
        tfStraatnaam = new TextField();
        tfPostcode = new TextField();
        tfToevoeging = new TextField();
        tfHuisnummer = new TextField();
        tfWoonplaats = new TextField();
        btVoegToe = new Button("Voeg klant toe");

        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        gridPane.add(new Label("Voornaam:"), 0, 1);
        gridPane.add(tfVoornaam, 1, 1);
        gridPane.add(new Label("Tussenvoegsel:"), 0, 2);
        gridPane.add(tfTussenvoegsel, 1, 2);
        gridPane.add(new Label("Achternaam:"), 0, 3);
        gridPane.add(tfAchternaam, 1, 3);

        gridPane.add(new Label("E-mail:"), 0, 4);
        gridPane.add(tfEmail, 1, 4);
        gridPane.add(new Label("Straatnaam:"), 0, 5);
        gridPane.add(tfStraatnaam, 1, 5);
        gridPane.add(new Label("Huisnummer:"), 0, 6);
        gridPane.add(tfHuisnummer, 1, 6);
        gridPane.add(new Label("Toevoeging:"), 0, 7);
        gridPane.add(tfToevoeging, 1, 7);

        gridPane.add(new Label("Postcode:"), 0, 8);
        gridPane.add(tfPostcode, 1, 8);
        gridPane.add(new Label("Woonplaats:"), 0, 9);
        gridPane.add(tfWoonplaats, 1, 9);
        gridPane.add(btVoegToe, 1, 10);

        gridPane.setAlignment(Pos.CENTER);
        tfVoornaam.setAlignment(Pos.BOTTOM_LEFT);
        tfAchternaam.setAlignment(Pos.BOTTOM_LEFT);
        tfTussenvoegsel.setAlignment(Pos.BOTTOM_LEFT);
        tfEmail.setAlignment(Pos.BOTTOM_LEFT);
        tfStraatnaam.setAlignment(Pos.BOTTOM_LEFT);
        tfPostcode.setAlignment(Pos.BOTTOM_LEFT);
        tfToevoeging.setAlignment(Pos.BOTTOM_LEFT);
        tfHuisnummer.setAlignment(Pos.BOTTOM_LEFT);
        tfWoonplaats.setAlignment(Pos.BOTTOM_LEFT);

        scene = new Scene(gridPane, 400, 400);
        stMaakNieuweKlant = new Stage();
        stMaakNieuweKlant.setTitle("Voeg klant toe");
        stMaakNieuweKlant.setScene(scene);

    }

    public void setVoegToeHandler(EventHandler<ActionEvent> eh) {
        btVoegToe.setOnAction(eh);
    }

    public Klant getKlantInfo() {

        Klant klant = new Klant();
//        GenericValidator controle = new GenericValidator();

        boolean goedIngevuld = true;

        String voornaam = tfVoornaam.getText();
        if ((voornaam.matches(".*\\d.*")) || (GenericValidator.isBlankOrNull(voornaam))) {
            tfVoornaam.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;

        }
        else{
            tfVoornaam.setStyle("-fx-text-inner-color:black;");
        }
        String achternaam = tfAchternaam.getText();
        if ((achternaam.matches(".*\\d.*")) || (GenericValidator.isBlankOrNull(achternaam))) {
            tfAchternaam.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
        else{
            tfAchternaam.setStyle("-fx-text-inner-color:black;");
        }
        String tussenvoegsel = tfTussenvoegsel.getText();
        if (tussenvoegsel.matches(".*\\d.*")) {
            tfTussenvoegsel.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
                else{
            tfTussenvoegsel.setStyle("-fx-text-inner-color:black;");
        }
        String email = tfEmail.getText();
        if ((!GenericValidator.isEmail(email)) || (GenericValidator.isBlankOrNull(email))) {
            goedIngevuld = false;
            tfEmail.setStyle("-fx-text-inner-color:red;");
        }
        else{
            tfEmail.setStyle("-fx-text-inner-color:black;");
        }
        String straatnaam = tfStraatnaam.getText();
        if ((straatnaam.matches(".*\\d.*")) || (GenericValidator.isBlankOrNull(straatnaam))) {
            tfStraatnaam.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
                else{
            tfStraatnaam.setStyle("-fx-text-inner-color:black;");
        }
        String postcode = tfPostcode.getText().toUpperCase();
        if ((postcode.length() != 6) || (!postcode.matches("\\d{4}[A-Z]{2}"))) {
            tfPostcode.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
        else{
            tfPostcode.setStyle("-fx-text-inner-color:black;");
        }
        String toevoeging = tfTussenvoegsel.getText();

        String huisnummer = tfHuisnummer.getText();
        if ((huisnummer.matches(".*\\D.*")) || (GenericValidator.isBlankOrNull(huisnummer))) {
            tfHuisnummer.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
                else{
            tfHuisnummer.setStyle("-fx-text-inner-color:black;");
        }
        String woonplaats = tfWoonplaats.getText();
        if ((woonplaats.matches(".*\\d.*")) || (GenericValidator.isBlankOrNull(woonplaats))) {
            tfWoonplaats.setStyle("-fx-text-inner-color:red;");
            goedIngevuld = false;
        }
        else{
            tfWoonplaats.setStyle("-fx-text-inner-color:black;");
        }

        if (goedIngevuld) {
            klant.setVoornaam(voornaam);
            klant.setTussenvoegsel(tussenvoegsel);
            klant.setAchternaam(achternaam);
            klant.setStraatnaam(straatnaam);
            klant.setHuisnummer(Integer.parseInt(huisnummer));
            klant.setToevoeging(toevoeging);
            klant.setWoonplaats(woonplaats);
            klant.setEmail(email);
            klant.setPostcode(postcode);
            stMaakNieuweKlant.hide();
        } else {
            klant = null;
        }
        return klant;
    }
}