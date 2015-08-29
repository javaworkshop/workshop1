package crud;


import java.sql.SQLException;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Class from which the program runs. User input is processed through this class.
 */
public class Controller extends Application {
    private static final String MYSQL_URL = "jdbc:Mysql://localhost:3306/mydb";
    //private static final String FIREBIRD_URL = "???";
   
 
    		
    		
 
    
   public DatabaseConnector dbConnector = new DatabaseConnector.Builder().dataSource(null).isInitialized(false).build();
    private String lastExecutedQuery = "";
    
    private ErrorScreen errorScreen;
    private AddBestellingScreen addBestellingScreen;
    private AddArtikelScreen addArtikelScreen;
    private AddKlantScreen addKlantScreen;
    
    private TableView tableView = new TableView();
    private TextArea taSQLResult = new TextArea();
    private TextArea tasqlCommand = new TextArea();
    private TextField tfUsername = new TextField();
    private TextField tfURL = new TextField(MYSQL_URL);
    private TextField tfAantal = new TextField();
    private PasswordField pfPassword = new PasswordField();
    private ComboBox<String> cboDataSource = new ComboBox<>();
    private Button btExecuteSQL = new Button("Voer SQL Commando Uit");
    private Button btUpdate = new Button("Update");
    private Button btDelete = new Button("Delete");
    private Button btVoegBestelling = new Button("Voeg Bestelling Toe");
    private Button btVoegArtikel = new Button("Voeg Artikel Toe");
    private Button btClearSQLCommand = new Button("Leegmaken");
    private Button btConnectDB = new Button("Verbind Met Database");
    private Button btClearSQLResult = new Button("Scherm Leegmaken");
    private Button btRefresh = new Button("Ververs Tabel");
    private Button btNieuweKlant = new Button("Maak Nieuwe Klant");
    private Button btMaakKlanten = new Button("Maak Klanten Aan");
    private Label lblConnectionStatus = new Label("Geen verbinding ");
    private BorderPane borderPaneExecutionResult = new BorderPane();

    
    
    @Override
    public void start(Stage primaryStage) {   	
        cboDataSource.getItems().addAll(FXCollections.observableArrayList("Hikari_CP", "C3P0"));
        cboDataSource.getSelectionModel().selectFirst();
        
        GridPane gridPane = new GridPane();
        
        gridPane.add(tfURL, 1, 0);
        gridPane.add(cboDataSource, 1, 1);
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
        hBoxUpdate.getChildren().addAll(btRefresh, btNieuweKlant, btVoegArtikel, btVoegBestelling);
        
        
        HBox hBoxConnection = new HBox();
        hBoxConnection.getChildren().addAll(lblConnectionStatus, btConnectDB);
        hBoxConnection.setAlignment(Pos.CENTER_RIGHT);
        VBox vBoxConnection = new VBox(5);
        vBoxConnection.getChildren().addAll(new Label("Vul database waarden in"), gridPane,
                hBoxConnection, hBoxUpdate);
        gridPane.setStyle("-fx-border-color: black;");
        
        HBox hBoxSQLCommand = new HBox(5);
        hBoxSQLCommand.getChildren().addAll(
                btClearSQLCommand, btExecuteSQL, btDelete, btUpdate);
        hBoxSQLCommand.setAlignment(Pos.CENTER_RIGHT);
        
        BorderPane borderPaneSqlCommand = new BorderPane();
        borderPaneSqlCommand.setTop(new Label("Vul SQL Commando in"));
        borderPaneSqlCommand.setCenter(new ScrollPane(tasqlCommand));
        borderPaneSqlCommand.setBottom(hBoxSQLCommand);
        
        HBox hBoxConnectionCommand = new HBox(10);
        hBoxConnectionCommand.getChildren().addAll(vBoxConnection, borderPaneSqlCommand);
        
        
        borderPaneExecutionResult.setTop(  new Label("SQL uitvoer"));
        borderPaneExecutionResult.setCenter(taSQLResult);
        borderPaneExecutionResult.setBottom(btClearSQLResult);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBoxConnectionCommand);
        borderPane.setCenter(borderPaneExecutionResult);
        
        
        Scene scene = new Scene(borderPane, 1400, 700);
        primaryStage.setTitle("SQLClient"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        //Initialise windows
        //errorScreen = new ErrorScreen();
        //errorScreen.initOwner(primaryStage);
        addBestellingScreen = new AddBestellingScreen();
        addBestellingScreen.initOwner(primaryStage);
        addBestellingScreen.setVoegToeHandler(e -> addBestelling());
        addArtikelScreen = new AddArtikelScreen();
        addArtikelScreen.initOwner(primaryStage);
        addArtikelScreen.setVoegToeHandler(e -> addArtikel());
        addKlantScreen = new AddKlantScreen();
        addKlantScreen.initOwner(primaryStage);
        addKlantScreen.setVoegToeHandler(e -> addKlant());
        
        // Set button actions
        btConnectDB.setOnAction(e -> connectToDB());
        btExecuteSQL.setOnAction(e -> executeSQL());
        btClearSQLCommand.setOnAction(e -> tasqlCommand.setText(null));
        btClearSQLResult.setOnAction(e -> { 
            taSQLResult.setText(null);
            tableView.getColumns().clear();
            lastExecutedQuery = "";
                });
        btMaakKlanten.setOnAction(e -> {
            Thread th = new Thread(() -> createKlanten());
            th.setUncaughtExceptionHandler((t, ex) -> {
                taSQLResult.setText("Geen verbinding met database.");
            });
            th.start();
        });
        btVoegBestelling.setOnAction(e -> {
            if(dbConnector.isInitialized())
                addBestellingScreen.show();
            else taSQLResult.setText("Geen verbinding met database.");
        });
        btVoegArtikel.setOnAction(e -> {
            if(dbConnector.isInitialized())
                addArtikelScreen.show();
            else taSQLResult.setText("Geen verbinding met database.");
        });
        btNieuweKlant.setOnAction(e -> {
            if (dbConnector.isInitialized()) {
                addKlantScreen.stMaakNieuweKlant.show();
                
            } else {
                taSQLResult.setText("Geen verbinding met database.");
            }
        });
        btDelete.setOnAction(e -> {
            Thread th = new Thread(() -> delete());
            th.setUncaughtExceptionHandler((t, ex) -> {
                taSQLResult.setText("Geen verbinding met database.");
            });
            th.start();
        }); //nog te implementeren
        btUpdate.setOnAction(e -> {
            Thread th = new Thread(() -> update());
            th.setUncaughtExceptionHandler((t, ex) -> {
                taSQLResult.setText("Geen verbinding met database.");
            });
            th.start();
        });
        btRefresh.setOnAction(e -> refresh());
    }
    
    private void addBestelling() {
        Bestelling bestelling = new Bestelling();
        addBestellingScreen.processTextFields(bestelling);
        
        try {
            dbConnector.addBestelling(bestelling);
        }
        catch(SQLException ex) {            
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
        }
        catch (DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        
        addBestellingScreen.hide();
    }
    
    private void addArtikel() {
        Artikel artikel = new Artikel();
        addArtikelScreen.processArtikelInfo(artikel);
        
        try {
            dbConnector.addArtikel(artikel);
        }
        catch(SQLException ex) {            
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
        }
        catch (DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        
        addArtikelScreen.hide();
    }
    
    private void addKlant() {

        Klant klant = addKlantScreen.getKlantInfo();

        if (klant == null) {
            
            
            //       Bij Errorscherm popup verwijnt MaakKlantscherm naar de achterkant
            //          showExceptionPopUp("Sommige gevens zijn niet goed ingevuld.");
    
        } else {
            try {
                dbConnector.addKlant(klant);
            } catch (SQLException ex) {
                showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
            } catch (DatabaseException ex) {
                showExceptionPopUp(ex.getMessage());
            }

            addKlantScreen.hide();
        }

    }
    
    /**
     * Called when the Maak Klanten button is pressed. Uses the KlantGenerator class to create n 
     * random klanten and adds them to the database.
     */
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
    
    /**
     * Called when the Verbind Met Database button is pressed. Uses the driver and url comboboxes 
     * and username and password textfields to initate database connection.
     */
    private void connectToDB() { 
    	
    //	dbConnector = new DatabaseConnector.UserBuilder(null, false).url(tfURL.getText().trim()).password(pfPassword.getText().trim()).build();
    	/*
    	dbConnector.setUrl(tfURL.getText().trim());
    	dbConnector.setUsername(tfUsername.getText().trim());
    	dbConnector.setPassword(pfPassword.getText().trim());
          */  String dataSource = cboDataSource.getSelectionModel().getSelectedItem(); 
    	
	
    	
	try {
            if(dataSource.equals("Hikari_CP")) {
            	dbConnector = new DatabaseConnector.Builder().url(tfURL.getText().trim()).
            	username(tfUsername.getText().trim()).password(pfPassword.getText().trim())
            	.dataSourceType(DatabaseConnector.HIKARI_CP_DATASOURCE).driver(DatabaseConnector.HIKARI_CP_DRIVER).build();
            	//dbConnector.setDataSourceType(DatabaseConnector.HIKARI_CP_DATASOURCE);
                //dbConnector.setDriver(DatabaseConnector.HIKARI_CP_DRIVER);

            }
            else/*if(dataSource.equals("C3P0"))*/ {
            	dbConnector = new DatabaseConnector.Builder().url(tfURL.getText().trim()).
                    	username(tfUsername.getText().trim()).password(pfPassword.getText().trim())
                    	.dataSourceType(DatabaseConnector.C3P0_DATASOURCE).driver(DatabaseConnector.C3P0_DRIVER).build();
            	
            //	dbConnector.setDataSourceType(DatabaseConnector.C3P0_DATASOURCE);
              //   dbConnector.setDriver(DatabaseConnector.C3P0_DRIVER);      
            
            }
            dbConnector.connectToDatabase();
        }
        catch (DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
        
        lblConnectionStatus.setText("Verbonden met database ");
    }    
    
    /**
     * Called when the Voer SQL Uit button is pressed. Calls either the processSQLSelect() or the
     * processSQLNonSelect() method, depending on whether the SQL command statement entered in the
     * SQL Command text area is a SELECT statement (that returns a QueryResult object) or not. 
     */
    private void executeSQL(){
        String sqlCommands = tasqlCommand.getText().trim();
        String[] commands = sqlCommands.replace('\n', ' ').split(";");
        for (String aCommand: commands) {
            if (aCommand.trim().toUpperCase().startsWith("SELECT")) {
                processSQLSelect(aCommand);
                lastExecutedQuery = aCommand;
            }
            else
                processSQLNonSelect(aCommand);
        }
    }
    
    private void processSQLSelect(String sqlCommand) {
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
    
    private void update() {
        ArrayList<Data> dataToUpdate = new ArrayList<>();
        for(int i = 0; i < tableView.getItems().size(); i++) {
            DataDisplayRow currentRow = (DataDisplayRow)tableView.getItems().get(i);
            if(currentRow.getUpdate()) {
                dataToUpdate.add(currentRow.getKlant());
                dataToUpdate.add(currentRow.getBestelling());
                currentRow.setUpdate(false);
            }
        }
        try {
            dbConnector.batchUpdate(dataToUpdate);
        }
        catch (SQLException ex) {
            showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
        }
        catch(DatabaseException ex) {
            showExceptionPopUp(ex.getMessage());
        }
    }
    
    private void delete() {
        for(int i = 0; i < tableView.getItems().size(); i++) {
            DataDisplayRow currentRow = (DataDisplayRow)tableView.getItems().get(i);
            if(currentRow.getDelete()) {
                Klant k = currentRow.getKlant();
                Bestelling b = currentRow.getBestelling();
                try {
                    if(k.getKlant_id() != 0)
                        dbConnector.deleteKlant(k.getKlant_id());
                    if(b.getBestelling_id() != 0)
                        dbConnector.deleteBestelling(b.getBestelling_id());
                }
                catch (SQLException ex) {
                    showExceptionPopUp("SQL error!\nErrorcode: " + ex.getErrorCode());
                }
                catch(DatabaseException ex) {
                    showExceptionPopUp(ex.getMessage());
                }
                currentRow.setDelete(false);
            }
        }
    }
    
    private void refresh() {
        if(!lastExecutedQuery.equals(""))
            processSQLSelect(lastExecutedQuery);
    }

    private void populateTableView(QueryResult queryResult) {
        tableView.getColumns().clear(); // maak tableView leeg
        if(queryResult.isEmpty())
            return;
        
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
        for(int i = 0; i < queryResult.rowCount(); i++) {
            DataDisplayRow currentRow = new DataDisplayRow(queryResult.getRow(i));
            currentRow.makeExclusiveCheckBoxes();
            data.add(currentRow);
        }
        tableView.setItems(FXCollections.observableArrayList(data));
    }

    /**
     * Makes errorScreen pop up with the given message. As long as the error screen is open it will 
     * stay in front of the main screen.
     * @param message the message that will be shown inside the error screen
     */
    private void showExceptionPopUp(String message) {        
        errorScreen.setMessage(message);
        errorScreen.show();
    }
    public static void main(String[] args) {
        launch(args);
    }}
