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

    private void populateTableView(QueryResult queryResult) {
        tableView.getColumns().removeAll(tableView.getColumns());
        ObservableList<ObservableList> data = FXCollections.observableArrayList();    
        
        String[] columnNames = queryResult.columnNames();
            
        for (int i = 0; i < queryResult.columnCount(); i++) {
            final int j = i;
	    TableColumn col = new TableColumn(columnNames[i]);
            
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, 
                ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> 
                            param) {
                        if (param == null || param.getValue() == null || param.getValue().get(j) == 
                                null) {
                            return null;
                        }
                        return new SimpleStringProperty((String)param.getValue().get(j));
                    }
            });

            tableView.getColumns().add(col);
        }
        
        for (int i = 1; i <= queryResult.rowCount(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for(String columnName : columnNames) {           
                row.add(queryResult.getCellValue(columnName, i));
            }
            data.add(row);
        }
	    
        tableView.setItems(data);
    }
    
    private void populateTableView(ArrayList<Data> data) {
        tableView.getColumns().removeAll(tableView.getColumns());
        
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            columnNames = dbConnector.retrieveColumnNames(data.get(0));
        }
        catch(SQLException ex){
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
	}
        catch(DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        
        for(String columnName : columnNames) {
                TableColumn<Data, String> tc = new TableColumn<>(columnName);
                tc.setCellValueFactory(new PropertyValueFactory<Data, String>(
                        columnName));
                tableView.getColumns().add(tc);
            }
            
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
