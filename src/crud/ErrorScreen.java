package crud;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A general screen used to display error messages to the user. Use show method to get the screen to
 * pop up.
 */
class ErrorScreen extends Stage {
    Label errorMessage;
    
    ErrorScreen() {
        VBox mainPane = new VBox();
        
        ImageView errorIcon = new ImageView(new Image("image/error_icon.png"));
        errorMessage = new Label();
        errorMessage.setGraphic(errorIcon);
        errorMessage.setContentDisplay(ContentDisplay.LEFT);
        errorMessage.setStyle("-fx-graphic-text-gap: 10; "
                + "-fx-padding: 20 20 20 5; -fx-graphic-text-gap: 30;");
        StackPane errorMessagePane = new StackPane();
        errorMessagePane.setStyle(" -fx-background-color: #ffffff;");
        errorMessagePane.getChildren().add(errorMessage);
        mainPane.getChildren().add(errorMessagePane);
        
        Button btClose = new Button("Close");
        btClose.setAlignment(Pos.CENTER_RIGHT);
        btClose.setOnAction(e -> {
            hide();
        });
        StackPane buttonPane = new StackPane();
        buttonPane.setStyle("-fx-alignment: center-right; -fx-padding: 10;");
        buttonPane.getChildren().add(btClose);
        
        mainPane.getChildren().add(buttonPane);
        
        setScene(new Scene(mainPane, 340, 140)); 
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
