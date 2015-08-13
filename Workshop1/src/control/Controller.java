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
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * Class from which the program runs. User input is processed through this class.
 */
public class Controller extends Application {
    private DatabaseConnector dbConnector = new DatabaseConnector();
    
    private Stage primaryStage;
    
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
        this.primaryStage = primaryStage;
	primaryStage.setTitle("SQLClient"); // Set the stage title
	primaryStage.setScene(scene); // Place the scene in the stage
	primaryStage.show(); // Display the stage

	btConnectDB.setOnAction(e -> connectToDB());
	btExecuteSQL.setOnAction(e -> executeSQL());
	btClearSQLCommand.setOnAction(e -> tasqlCommand.setText(null));
	btClearSQLResult.setOnAction(e -> taSQLResult.setText(null));
	btMaakKlanten.setOnAction(e -> {
            Thread th = new Thread(() -> createKlanten());
            th.setUncaughtExceptionHandler((t, ex) -> {
                taSQLResult.setText("Geen verbinding met database.");
            });
            th.start();
	});
        btUpdate.setOnAction(e -> update()); //om te testen
    }
    
    private void createKlanten() {
        try {            
            int aantalKlanten = Integer.parseInt(tfAantal.getText().trim());
            /*if(klanten<=0)
                taSQLResult.setText("Geen klanten aangemaakt, voer correct aantal in");*/
            Klant[] klanten = KlantGenerator.generateNKlanten(aantalKlanten);            
            dbConnector.batchInsertion(klanten);
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
        catch(IllegalArgumentException ex) {
            showExceptionPopUp("Voer een getal in groter dan 0.");
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
        String sqlCommands = tasqlCommand.getText().trim();
        String[] commands = sqlCommands.replace('\n', ' ').split(";");
        for (String aCommand: commands) {
            if (aCommand.trim().toUpperCase().startsWith("SELECT"))
                processSQLSelect(aCommand);
            else
                processSQLNonSelect(aCommand);
        }
    }

    private void processSQLSelect(String sqlCommand){
	borderPaneExecutionResult.getChildren().remove(taSQLResult);
	borderPaneExecutionResult.setCenter(tableView);
	try {
            QueryResult queryResult = dbConnector.executeQuery(sqlCommand);
            populateTableView(queryResult);
	}
        catch(SQLException ex){
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
	}
        catch(DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
    }
    
    // ik denk dat update en delete alleen mogelijk moeten zijn als de primarykey ook in de tabel 
    // staat...
    private void update() {
        
    }

    // todo...
    private ArrayList<Integer> updateRowIndices() {
        ArrayList<Integer> rowIndices = new ArrayList<>();
        int columnIndex = 0;
        for(int i = 0; i < tableView.getColumns().size(); i++) {
            
        }
        
        return rowIndices;
    }
    
    // todo...
    private void delete() {
        
    }
    
    // todo...    
    private ArrayList<Integer> deleteRowIndices() {
        return new ArrayList<Integer>();
    }
    
    private void populateTableView(QueryResult queryResult) {
        tableView.getColumns().clear(); // maak tableView leeg
        
        String[] columnNames = queryResult.getColumnNames();
        
        // maak alle kolommen die in queryResult zijn opgeslagen
        for(int i = 0; i < columnNames.length; i++) {
            // maak kolom
            TableColumn<DataDisplayRow, String> col = new TableColumn<>(columnNames[i]);
            
            // bepaalt de waarde van een cell
            col.setCellValueFactory(new PropertyValueFactory<DataDisplayRow, String>(
                columnNames[i]));
             
            // maakt een cell editable, primary keys kunnen niet veranderd worden            
            if( !(columnNames[i].equals("klant_id") || columnNames[i].equals("bestelling_id")) ) {
                col.setCellFactory(TextFieldTableCell.forTableColumn());
                col.setEditable(true);
            }
             
            // voeg kolom toe aan tableview
            tableView.getColumns().add(col);
        }
        
        // Voeg update kolom toe
        TableColumn<DataDisplayRow, Boolean> updateCol = new TableColumn<>("update");
        updateCol.setCellValueFactory(new PropertyValueFactory<DataDisplayRow, Boolean>("update"));
        updateCol.setCellFactory(CheckBoxTableCell.forTableColumn(updateCol));
        updateCol.setEditable(true);
        tableView.getColumns().add(updateCol);
        
        // Voeg delete kolom toe
        TableColumn<DataDisplayRow, Boolean> deleteCol = new TableColumn<>("delete");
        deleteCol.setCellValueFactory(new PropertyValueFactory<DataDisplayRow, Boolean>("delete"));
        deleteCol.setCellFactory(CheckBoxTableCell.forTableColumn(deleteCol));
        deleteCol.setEditable(true);
        tableView.getColumns().add(deleteCol);
        
        tableView.setEditable(true);        
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().clearSelection();
        
        // voegt data toe aan tableView
        ArrayList<DataDisplayRow> data = new ArrayList<>();
        for(int i = 0; i < queryResult.rowCount(); i++)
            data.add(new DataDisplayRow(queryResult.getRow(i)));        
        tableView.setItems(FXCollections.observableArrayList(data));
    }       

    private void processSQLNonSelect(String sqlCommand) {
        borderPaneExecutionResult.getChildren().remove(tableView);
	borderPaneExecutionResult.setCenter(taSQLResult);

        try {
            dbConnector.executeCommand(sqlCommand);
            taSQLResult.setText("SQL commando uitgevoerd");
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
        es.initOwner(primaryStage);
        es.setMessage(message);
        es.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
