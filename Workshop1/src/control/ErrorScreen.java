package control;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ContentDisplay;

/**
 * Een algemeen scherm om foutmeldingen aan de gebruiker door te geven. Gebruik show() methode om
 * scherm weer te geven.
 */
class ErrorScreen extends Stage {
    Label errorMessage;
    
    ErrorScreen() {
        VBox mainPane = new VBox();
        ImageView errorIcon = new ImageView(new Image("img/error_icon.png"));
        errorMessage = new Label();
        errorMessage.setGraphic(errorIcon);
        errorMessage.setContentDisplay(ContentDisplay.LEFT);
        Button btClose = new Button("Close");
        btClose.setAlignment(Pos.CENTER_RIGHT);
        btClose.setOnAction(e -> {
            hide();
        });
        mainPane.getChildren().addAll(errorMessage, btClose);
        setScene(new Scene(mainPane, 400, 250));
    }
    
    ErrorScreen(String title, String message) {
        this();
        setTitle(title);
        errorMessage.setText(message);        
    }
    
    void setMessage(String message) {
        errorMessage.setText(message);
    }
    
    String getMessage() {
        return errorMessage.getText();
    }
    
}
