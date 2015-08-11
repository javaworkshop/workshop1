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
        btUpdate.setOnAction(e -> findUpdateRows()); //om te testen
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
        tableView.getColumns().clear(); // verwijdert huidige kolommen uit tableView
        ObservableList<ObservableList> data = FXCollections.observableArrayList();    
        
        String[] columnNames = queryResult.columnNames();
        
        // maakt alle kolommen die in queryResult zijn opgeslagen
        for (int i = 0; i < queryResult.columnCount(); i++) {
            final int j = i;
	    TableColumn col = new TableColumn(columnNames[i]);
            
            // Bepaalt de waarde van een cell
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
            
            // Maakt een cell editable
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setOnEditCommit(new EventHandler<CellEditEvent<ObservableList, String>>() {
                @Override
                public void handle(CellEditEvent<ObservableList, String> t) {                    
                    ObservableList<String> cellValues = (ObservableList)tableView.getItems().get(
                            t.getTablePosition().getRow());
                    cellValues.set(j, t.getNewValue());
                }
            });
            
            tableView.getColumns().add(col);
        }
        
        // Voegt de data uit queryResult toe aan tableView
        for (int i = 1; i <= queryResult.rowCount(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for(String columnName : columnNames) {           
                row.add(queryResult.getCellValue(columnName, i));
            }
            data.add(row);
        }   
        
        // Maakt update kolom met checkboxes
	TableColumn<ObservableList,CheckBox> updateCol = new TableColumn<>();
	updateCol.setText("update");
	        
	updateCol.setCellFactory(new Callback<TableColumn<ObservableList, CheckBox>, 
                TableCell<ObservableList, CheckBox>>() {
	    public TableCell<ObservableList, CheckBox> call(TableColumn<ObservableList, CheckBox> p) {
                return new CheckBoxTableCell<ObservableList, CheckBox>(
                        /*new Callback<Integer, ObservableValue<Boolean>>() {
                            public ObservableValue<Boolean> call(int i) {
                                return new 
                            }
                        }*/);
            }
	});     
        
        tableView.getColumns().add(updateCol);
        tableView.setItems(data);
        tableView.setEditable(true);        
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().clearSelection();
        data.removeAll(tableView.getSelectionModel().getSelectedItems());
    }
    
    /**
     * Finds the indices of the rows in the tableview that have their update cell checked.
     * @return an array containing all the row numbers that have their update cell checked
     */
    private ArrayList<Integer> findUpdateRows() {
        int columnCount = tableView.getColumns().size();
        int updateColIndex;
        for(updateColIndex = 0; updateColIndex < columnCount; updateColIndex++) {
            String colText = ((TableColumn)tableView.getColumns().get(updateColIndex)).getText();
            if(colText.equals("update")) {
                break;
            }
        }
        
        ObservableList<ObservableList> table = tableView.getItems();       
        ArrayList<Integer> rowIndices = new ArrayList<>();
        int rowCount = table.size();
        for(int i = 0; i < rowCount; i++) {
            CheckBox cb = (CheckBox)table.get(i).get(updateColIndex);
            if(cb.isSelected())
                rowIndices.add(i);
        }
        
        return rowIndices;
    }
    
    /**
     * Finds the indices of the rows in the tableview that have their delete cell checked.
     * @return an array containing all the row numbers that have their delete cell checked
     */
    private int[] findDeleteRows() {
        return new int[1];
    }
    
    /*public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
        private final CheckBox checkBox;
        //private ObservableValue<T> ov;

	public CheckBoxTableCell() {

            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);

            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }
    }*/
    
    private void populateTableView(ArrayList<Data> data) {
        tableView.getColumns().clear();
        
        String[] columnNames = Data.getAttributeNames(data.get(0));
        
        for(String columnName : columnNames) {
                TableColumn<Data, String> tc = new TableColumn<>(columnName);
                tc.setCellValueFactory(new PropertyValueFactory<Data, String>(
                        columnName));
                tableView.getColumns().add(tc);
        }
        
        // Misschien is het nodig om het zo te doen, methode moet nog getest worden, dus ben niet 
        // zeker
        /*
        if(data.get(0) instanceof Klant) {
            for(String columnName : columnNames) {
                    TableColumn<Klant, String> tc = new TableColumn<>(columnName);
                    tc.setCellValueFactory(new PropertyValueFactory<Klant, String>(
                            columnName));
                    tableView.getColumns().add(tc);
            }
        }
        else if(data.get(0) instanceof Bestelling) {
            for(String columnName : columnNames) {
                    TableColumn<Bestelling, String> tc = new TableColumn<>(columnName);
                    tc.setCellValueFactory(new PropertyValueFactory<Bestelling, String>(
                            columnName));
                    tableView.getColumns().add(tc);
            }
        }
        */ 
        /*
        else if(data.get(0) instanceof Artikel) {
            for(String columnName : columnNames) {
                    TableColumn<Klant, String> tc = new TableColumn<>(columnName);
                    tc.setCellValueFactory(new PropertyValueFactory<Artikel, String>(
                            columnName));
                    tableView.getColumns().add(tc);
            }
        }
        */
            
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
