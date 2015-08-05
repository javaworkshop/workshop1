package control;

import database.*;
import model.*;
import java.sql.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Class from which the program runs. User input is processed through this class.
 */
public class Controller extends Application {
    private DatabaseConnector dbConnector = new DatabaseConnector();
    
    private TableView tableView = new TableView();
    private TextArea taSQLResult = new TextArea();
    private TextArea tasqlCommand = new TextArea();
    private TextField tfUsername = new TextField();
    private TextField tfURL = new TextField();
    private TextField tfAantal = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private ComboBox<String> cboURL = new ComboBox<>();
    private ComboBox<String> cboDriver = new ComboBox<>();
    private Button btExecuteSQL = new Button("Execute SQL Command");
    private Button btUpdate = new Button("Update");
    private Button btVoegBestelling = new Button("Voeg Bestelling toe");
    private Button btVoegArtikel = new Button("Voeg Artikel");
    private Button btClearSQLCommand = new Button("Clear");
    private Button btConnectDB = new Button("Connect to Database");
    private Button btClearSQLResult = new Button("Clear Result");
    private Button btVervers = new Button("Ververs tabel");
    private Button btNieuweKlant = new Button("Maak nieuwe klant");
    private Button btMaakKlanten = new Button("maak klanten aan");
    private Label lblConnectionStatus = new Label("No connection now ");
    private BorderPane borderPaneExecutionResult = new BorderPane();

    @Override
    public void start(Stage primaryStage) {        
    	cboURL.getItems().addAll(FXCollections.observableArrayList(
            "jdbc:Mysql://localhost:3306/mydb",
            "jdbc:mysql://liang.armstrong.edu/mydb",
            "jdbc:odbc:exampleMDBDataSource",
            "jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl"));
	cboURL.getSelectionModel().selectFirst();
        cboDriver.getItems().addAll(FXCollections.observableArrayList(
            "com.mysql.jdbc.Driver", "sun.jdbc.odbc.dbcOdbcDriver",
            "oracle.jdbc.driver.OracleDriver"));
        cboDriver.getSelectionModel().selectFirst();

	GridPane gridPane = new GridPane();

	gridPane.add(cboURL, 1, 0);
	gridPane.add(cboDriver, 1, 1);
	gridPane.add(tfUsername, 1, 2);
	gridPane.add(pfPassword, 1, 3);
	gridPane.add(new Label("url datasource"), 0, 0);
	gridPane.add(new Label("Datasource"),0, 1);
	gridPane.add(new Label("gebruikersnaam"), 0, 2);
	gridPane.add(new Label("wachtwoord"), 0, 3);
	gridPane.add(new Label("     maak random klanten"),2,0);
	gridPane.add(new Label("     Voer aantal in: "), 2, 1);
	gridPane.add(tfAantal, 3, 1);
	gridPane.add(btMaakKlanten, 2, 2);
	HBox hBoxUpdate = new HBox();
	hBoxUpdate.getChildren().addAll(btVervers, btNieuweKlant, btVoegArtikel, btVoegBestelling);


	HBox hBoxConnection = new HBox();
	hBoxConnection.getChildren().addAll(lblConnectionStatus, btConnectDB);
	hBoxConnection.setAlignment(Pos.CENTER_RIGHT);
	VBox vBoxConnection = new VBox(5);
	vBoxConnection.getChildren().addAll(new Label("Enter Database Information"), gridPane, 
            hBoxConnection, hBoxUpdate);
	gridPane.setStyle("-fx-border-color: black;");

	HBox hBoxSQLCommand = new HBox(5);
	hBoxSQLCommand.getChildren().addAll(
	btClearSQLCommand, btExecuteSQL, btUpdate);
	hBoxSQLCommand.setAlignment(Pos.CENTER_RIGHT);

	BorderPane borderPaneSqlCommand = new BorderPane();
	borderPaneSqlCommand.setTop(  new Label("Enter an SQL Command"));
	borderPaneSqlCommand.setCenter(new ScrollPane(tasqlCommand));
	borderPaneSqlCommand.setBottom(hBoxSQLCommand);

        HBox hBoxConnectionCommand = new HBox(10);
	hBoxConnectionCommand.getChildren().addAll(vBoxConnection, borderPaneSqlCommand);


	borderPaneExecutionResult.setTop(  new Label("SQL Execution Result"));
	borderPaneExecutionResult.setCenter(taSQLResult);
	borderPaneExecutionResult.setBottom(btClearSQLResult);

	BorderPane borderPane = new BorderPane();
	borderPane.setTop(hBoxConnectionCommand);
	borderPane.setCenter(borderPaneExecutionResult);


	Scene scene = new Scene(borderPane, 1400, 700);
	primaryStage.setTitle("SQLClient"); // Set the stage title
	primaryStage.setScene(scene); // Place the scene in the stage
	primaryStage.show(); // Display the stage

	btConnectDB.setOnAction(e -> connectToDB());
	btExecuteSQL.setOnAction(e -> executeSQL());
	btClearSQLCommand.setOnAction(e -> tasqlCommand.setText(null));
	btClearSQLResult.setOnAction(e -> taSQLResult.setText(null));
	btMaakKlanten.setOnAction(e -> {
            //new Thread(() -> batchUpdate()).start();
            batchUpdate();
	});
    }
    
    // todo: klantgenerator aanmaken die random klant objecten retourneert en sql commando's
    // verplaatsen naar databaseconnector methode.
    private void batchUpdate() {
        try {
            String aantalKlanten = tfAantal.getText().trim();
            int klanten = Integer.parseInt(aantalKlanten);
            if(klanten<=0)
                taSQLResult.setText("Geen klanten aangemaakt, voer correct aantal in");
            else{
                String[] voornaam = {"Hani" , "Gerbrich", "Sander", "Maarten", "Remi" , "Rob" , 
                    "Bo" , "Jan" , "Willem" , " Piet"};
                String[] tussenvoegsel = {"van", "de", "el","","van de", "van der" , "ten", 
                    "van de", "uit het", "voor den"};
                String[] achternaam = {"de Jong", "Bakker", "Visser", "de Boer", "Peters","de " + 
                        "Graaf", "Jacobs", "Ali", "Hassan", "Beatrix"};
                String[] straatnaam = {"Schoolstraat", "Dorpstraat", "Nieuwstraat", "Kastanjelaan", 
                    "Eikenlaan", "Stationsweg","Markt","Beukenlaan", "Industrieweg","Molenstraat"};
                String[] postcode = {"2001aa" , "2002aa", "2003aa","2003ab","2005aa","2006aa", 
                    "2007aa","2008aa","2009aa","2010aa"};
                for ( int i = 1; i<=klanten ; i++ ){
                    String sqlCommand = "INSERT INTO klant VALUES(NULL, '" +
                        voornaam[(int)(Math.random()*10)] + "', '" + 
                        tussenvoegsel[(int)(Math.random()*10)] + "', '" + 
                        achternaam[(int)(Math.random()*10)] + "', NULL, '" + 
                        straatnaam[(int)(Math.random()*10)] + "', " + 
                        (int)(Math.random()*500)+", NULL, '" + postcode[(int)(Math.random()*10)] 
                        + "', 'Amsterdam')";
                    dbConnector.addBatch(sqlCommand);
                }
                dbConnector.executeBatch();
            }
	}
	catch(SQLException ex) {            
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
        }
        catch (DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        catch(NumberFormatException ex) {
            showExceptionPopUp("Voer een geheel getal in.");
        }
    }
    
    private void connectToDB() {
	dbConnector.setDriver(cboDriver.getSelectionModel().getSelectedItem());
	dbConnector.setUrl(cboURL.getSelectionModel().getSelectedItem());
	dbConnector.setUsername(tfUsername.getText().trim());
	dbConnector.setPassword(pfPassword.getText().trim());
	try {
            dbConnector.connectToDatabase();
        }
        catch(SQLException ex) {            
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
        }
        catch (DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        
        lblConnectionStatus.setText("Connected to database ");
    }    

    private void executeSQL() {
	if(!dbConnector.isInitialized()) {
            taSQLResult.setText("Please connect first");
	}
        else {
            String sqlCommands = tasqlCommand.getText().trim();
            String[] commands = sqlCommands.replace('\n', ' ').split(";");
            for (String aCommand: commands) {
		if (aCommand.trim().toUpperCase().startsWith("SELECT")) {
                    processSQLSelect(aCommand);
		}
                else {
                    processSQLNonSelect(aCommand);
		}
            }
	}
    }

    private void processSQLSelect(String sqlCommand){
	borderPaneExecutionResult.getChildren().remove(taSQLResult);
	borderPaneExecutionResult.setCenter(tableView);
	try {
            dbConnector.executeCommand(sqlCommand);
            populateTableView();
	}
        catch(SQLException ex){
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
	}
        catch(DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
    }

    private void populateTableView() {
        tableView.getItems().removeAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();    
        
        try {
            String[] columnNames = dbConnector.getCurrentColumnNames();
            
            for (int i = 0; i < columnNames.length; i++) {
	        final int j = i;
	        TableColumn col = new TableColumn(columnNames[i]);

                // col.setCellValueFactory(TextFieldTableCell.forTableColumn());
		col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, 
                        ObservableValue<String>>() {
		    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> 
                            param) {
		        if (param == null || param.getValue() == null || param.getValue().get(j) == 
                                null) {
		            return null;
		        }
		        return new SimpleStringProperty(param.getValue().get(j).toString());
		    }
		});

                tableView.getColumns().addAll(col);
            }

	    while (dbConnector.nextRow()) {
		ObservableList<String> row = FXCollections.observableArrayList();
		for (int i = 1; i <= columnNames.length; i++) {
		    row.add(dbConnector.getCurrentString(i));
		}
		System.out.println("Row [1] added " + row);
		    data.add(row);
            }
	    
            tableView.setItems(data);
	} 
        catch (SQLException ex) {
	    showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
	}        
    }

    private void processSQLNonSelect(String sqlCommand) {
        borderPaneExecutionResult.getChildren().remove(tableView);
	borderPaneExecutionResult.setCenter(taSQLResult);

        try {
            dbConnector.executeCommand(sqlCommand);
            taSQLResult.setText("SQL command executed");
	}
	catch (SQLException ex) {
	    showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
	}
        catch(DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
    }
    
    private void showExceptionPopUp(String message) {
        ErrorScreen es = new ErrorScreen();
        es.setMessage(message);
        es.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
