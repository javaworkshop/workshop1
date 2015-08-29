package crud;

import com.mchange.v2.c3p0.DataSources;

import com.sun.rowset.JdbcRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.sql.RowSet;

/**
 * Class that establishes and maintains a connection with the database and through which all sql
 * operations are processed.
 */
public class DatabaseConnector {
	public static final byte HIKARI_CP_DATASOURCE = 1;
	public static final byte C3P0_DATASOURCE = 2;
	public static final String HIKARI_CP_DRIVER = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
	public static final String C3P0_DRIVER = "com.mysql.jdbc.Driver";

	DataSource dataSource;

	private boolean isInitialized;

	private byte dataSourceType;
	private String driver;
	private String url;
	private String username;
	private String password;

	/*public DatabaseConnector() {
		dataSource = null;
		isInitialized = false;
	}
*/

	/**
	 * Initializes connection to database. Before this method is run driver, url, username, and 
	 * password should be set using the appropriate setter methods.
	 * @throws DatabaseException thrown if driver could not be loaded
	 */
	public void connectToDatabase() throws DatabaseException {
		if (dataSourceType == HIKARI_CP_DATASOURCE)
			setUpHikariCPDataSource();
		else
			/*if(dataSourceType == C3P0_DATASOURCE)*/
			setUpC3p0DataSource();

		isInitialized = true;
	}

	private void setUpC3p0DataSource() throws DatabaseException {
		try {
			Class.forName(driver);
		} catch (Exception ex) {
			throw new DatabaseException("Driver laden mislukt.", ex);
		}

		try {
			DataSource unpooledDS = DataSources.unpooledDataSource(url,
					username, password);
			dataSource = DataSources.pooledDataSource(unpooledDS);
		} catch (SQLException ex) {
			throw new DatabaseException("Verbinden mislukt.\n"
					+ "Controleer gebruikersnaam en wachtwoord.", ex);
		}
	}

	private void setUpHikariCPDataSource() throws DatabaseException {
		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(5);
		config.setInitializationFailFast(true);
		config.setLeakDetectionThreshold(5000);

		config.setDataSourceClassName(driver);
		config.addDataSourceProperty("serverName", "localhost"); // voor deze attributen moet 
		config.addDataSourceProperty("port", "3306"); // eigenlijk info uit de gui 
		config.addDataSourceProperty("databaseName", "mydb"); // gebruikt worden
		config.addDataSourceProperty("user", username);
		config.addDataSourceProperty("password", password);

		dataSource = new HikariDataSource(config);
		testConnection();
	}

	private void testConnection() throws DatabaseException {
		try (Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			statement.execute("SHOW TABLES");
		} catch (SQLException ex) {
			throw new DatabaseException("Verbinden mislukt.\n"
					+ "Controleer gebruikersnaam en wachtwoord.", ex);
		}
	}

	private RowSet createRowSet(String query) throws SQLException {
		//RowSetFactory rowSetFactory = RowSetProvider.newFactory(); Hoe te combineren met DataSource?
		//JdbcRowSet rowSet = rowSetFactory.createJdbcRowSet();      Met deze methode krijg je geen warning. 
		RowSet rowSet = new JdbcRowSetImpl(dataSource.getConnection());
		rowSet.setCommand(query);
		rowSet.execute();
		return rowSet;
	}

	/**
	 * Executes the given query. The contents of the resulting ResultSet are transferred to a 
	 * QueryResult object, which is returned to the caller.
	 * @param query                 the query to be executed
	 * @return                      a QueryResult object containining query results
	 * @throws SQLException
	 * @throws DatabaseException     thrown if database connection has not been initialized yet
	 */
	public QueryResult executeQuery(String query) throws SQLException,
			DatabaseException {
		executeCommand(query);
		QueryResult queryResult = null;
		try (RowSet rowSet = createRowSet(query);) {
			queryResult = createQueryResult(rowSet);
		}
		return queryResult;
	}

	// De switch in deze methode is niet zo mooi (erg lang). Misschien is er een betere manier?
	/**
	 * Retrieves results from the last executed query from rowSet and stores them in a QueryResult
	 * object.
	 * @return              the QueryResult object containing retrieved data
	 * @throws SQLException 
	 */
	private QueryResult createQueryResult(RowSet rowSet) throws SQLException {
		int columnCount = rowSet.getMetaData().getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = rowSet.getMetaData().getColumnName(i + 1);

		QueryResult queryResult = new QueryResult();

		while (rowSet.next()) {
			QueryResultRow row = new QueryResultRow();
			for (int i = 1; i <= columnCount; i++) {
				switch (columnNames[i - 1]) {
				// klant kolommen
				case "klant_id":
					row.getKlant().setKlant_id(rowSet.getInt(i));
					row.getBestelling().setKlant_id(rowSet.getInt(i));
					queryResult.addColumnName("klant_id");
					break;
				case "voornaam":
					row.getKlant().setVoornaam(rowSet.getString(i));
					queryResult.addColumnName("voornaam");
					break;
				case "tussenvoegsel":
					row.getKlant().setTussenvoegsel(rowSet.getString(i));
					queryResult.addColumnName("tussenvoegsel");
					break;
				case "achternaam":
					row.getKlant().setAchternaam(rowSet.getString(i));
					queryResult.addColumnName("achternaam");
					break;
				case "email":
					row.getKlant().setEmail(rowSet.getString(i));
					queryResult.addColumnName("email");
					break;
				case "straatnaam":
					row.getKlant().setStraatnaam(rowSet.getString(i));
					queryResult.addColumnName("straatnaam");
					break;
				case "huisnummer":
					row.getKlant().setHuisnummer(rowSet.getInt(i));
					queryResult.addColumnName("huisnummer");
					break;
				case "toevoeging":
					row.getKlant().setToevoeging(rowSet.getString(i));
					queryResult.addColumnName("toevoeging");
					break;
				case "postcode":
					row.getKlant().setPostcode(rowSet.getString(i));
					queryResult.addColumnName("postcode");
					break;
				case "woonplaats":
					row.getKlant().setWoonplaats(rowSet.getString(i));
					queryResult.addColumnName("woonplaats");
					break;
				// bestelling kolommen
				case "bestelling_id":
					row.getBestelling().setBestelling_id(rowSet.getInt(i));
					queryResult.addColumnName("bestelling_id");
					break;
				case "artikel_id1":
					row.getBestelling().setArtikel_id1(rowSet.getInt(i));
					queryResult.addColumnName("artikel_id1");
					break;
				case "artikel_id2":
					row.getBestelling().setArtikel_id2(rowSet.getInt(i));
					queryResult.addColumnName("artikel_id2");
					break;
				case "artikel_id3":
					row.getBestelling().setArtikel_id3(rowSet.getInt(i));
					queryResult.addColumnName("artikel_id3");
					break;
				case "artikel_naam1":
					row.getBestelling().setArtikel_naam1(rowSet.getString(i));
					queryResult.addColumnName("artikel_naam1");
					break;
				case "artikel_naam2":
					row.getBestelling().setArtikel_naam2(rowSet.getString(i));
					queryResult.addColumnName("artikel_naam2");
					break;
				case "artikel_naam3":
					row.getBestelling().setArtikel_naam3(rowSet.getString(i));
					queryResult.addColumnName("artikel_naam3");
					break;
				case "artikel_aantal1":
					row.getBestelling().setArtikel_aantal1(rowSet.getInt(i));
					queryResult.addColumnName("artikel_aantal1");
					break;
				case "artikel_aantal2":
					row.getBestelling().setArtikel_aantal2(rowSet.getInt(i));
					queryResult.addColumnName("artikel_aantal2");
					break;
				case "artikel_aantal3":
					row.getBestelling().setArtikel_aantal3(rowSet.getInt(i));
					queryResult.addColumnName("artikel_aantal3");
					break;
				case "artikel_prijs1":
					row.getBestelling().setArtikel_prijs1(rowSet.getDouble(i));
					queryResult.addColumnName("artikel_prijs1");
					break;
				case "artikel_prijs2":
					row.getBestelling().setArtikel_prijs2(rowSet.getDouble(i));
					queryResult.addColumnName("artikel_prijs2");
					break;
				case "artikel_prijs3":
					row.getBestelling().setArtikel_prijs3(rowSet.getDouble(i));
					queryResult.addColumnName("artikel_prijs3");
					break;
				}
			}
			queryResult.addRow(row);
		}

		return queryResult;
	}

	/**
	 * Executes the given command. Results are stored in rowSet if the command is a query.
	 * @param command               the command to be executed
	 * @throws SQLException         
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void executeCommand(String command) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		try (Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			statement.execute(command);
		}
	}

	public void executeDelete(String klant_id) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		String SQL = "DELETE FROM KLANT WHERE KLANT_ID = " + klant_id;
		try (Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			statement.executeUpdate(SQL);
		}
	}

	/**
	 * Inserts an array of klanten into the database using a batch statement.
	 * @param klanten               the array of klanten to be inserted
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void batchInsertion(Klant[] klanten) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		try (Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			for (Klant klant : klanten)
				statement.addBatch(SqlCodeGenerator
						.generateKlantInsertionCode(klant));
			statement.executeBatch();
		}
	}

	/**
	 * Updates the database tables with the values contained in the Data objects in the given 
	 * ArrayList using a batch statement.
	 * @param data                  the ArrayList containing the new values
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void batchUpdate(ArrayList<Data> data) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		try (Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			for (Data d : data) {
				if (d instanceof Klant) {
					if (((Klant) d).getKlant_id() != 0)
						statement.addBatch(SqlCodeGenerator
								.generateKlantUpdateCode(((Klant) d)));
				} else //if(d instanceof Bestelling)
				if (((Bestelling) d).getBestelling_id() != 0)
					statement.addBatch(SqlCodeGenerator
							.generateBestellingUpdateCode(((Bestelling) d)));
			}

			statement.executeBatch();
		}
	}

	/**
	 * Reads all customer data currently present in the database and stores them in an arraylist
	 * object.
	 * @return                      an arraylist object containing klant objects for all customers 
	 *                              stored in the database
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public ArrayList<Klant> readAll() throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		ArrayList<Klant> klanten = new ArrayList<>();
		try (RowSet rowSet = createRowSet("SELECT * FROM klant")) {
			while (rowSet.next())
				klanten.add(retrieveKlant(rowSet));
		}
		return klanten;
	}

	/**
	 * Adds the given klant to the database. The klant id (primary key) is generated automatically
	 * by the database.
	 * @param k                     the klant to be added
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void addKlant(Klant k) throws SQLException, DatabaseException {
		executeCommand(SqlCodeGenerator.generateKlantInsertionCode(k));
	}

	/**
	 * Adds the given bestelling to the database. The bestellling id (primary key) is generated 
	 * automatically by the database.
	 * @param b                     the bestelling to be added
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void addBestelling(Bestelling b) throws SQLException,
			DatabaseException {
		executeCommand(SqlCodeGenerator.generateBestellingInsertionCode(b));
	}

	/**
	 * Adds the given artikel to the database. It is stored as part of the bestelling that has the
	 * same value for bestelling id as the Artikel object.
	 * @param a                     the artikel to be added
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void addArtikel(Artikel a) throws SQLException, DatabaseException {
		executeCommand(SqlCodeGenerator.generateArtikelUpdateCode(a));
	}

	/**
	 * Returns the klant stored in the database with the given klant id. Klant data is returned in a
	 * klant object.
	 * @param klant_id              the id of the klant that is to be retrieved
	 * @return                      the klant with the given id
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public Klant readKlant(int klant_id) throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		Klant klant = null;
		try (RowSet rowSet = createRowSet("SELECT * FROM klant WHERE klant_id = "
				+ klant_id)) {
			rowSet.next();
			klant = retrieveKlant(rowSet);
		}

		return klant;
	}

	/**
	 * Returns all klanten stored in the database of whom the attribute values correspond to 
	 * attribute values of the given klant object. Only the attributes that have been specifically
	 * set in the klant object are used for the query. Klanten are returned in an arraylist object.
	 * @param k                     the klant object that contains query conditions
	 * @return                      klanten that have the same attribute values as the given klant
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public ArrayList<Klant> read(Klant k) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		String sqlcode = SqlCodeGenerator.generateKlantSelectionCode(k);
		ArrayList<Klant> klanten = new ArrayList<>();

		try (RowSet rowSet = createRowSet(sqlcode)) {
			while (rowSet.next())
				klanten.add(retrieveKlant(rowSet));
		}

		return klanten;
	}

	/**
	 * Returns the bestelling stored in the database with the given bestelling id. Bestelling data
	 * is returned in a Bestelling object.
	 * @param bestelling_id         the id of the bestelling that is to be retrieved
	 * @return                      the bestelling with the given id
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public Bestelling readBestelling(int bestelling_id) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		Bestelling bestelling = null;
		try (RowSet rowSet = createRowSet("SELECT * FROM bestelling WHERE bestelling_id = "
				+ bestelling_id)) {
			rowSet.next();
			bestelling = retrieveBestelling(rowSet);
		}

		return bestelling;
	}

	/**
	 * Retrieves klant data from rowSet and stores them in a Klant object. The klant data that is
	 * retrieved comes from the row to which the cursor in rowSet is currently pointing. The method
	 * assumes rowSet contains data from all columns in the klant table.
	 * @return              the Klant object with data from the currently selected row in rowSet
	 * @throws SQLException 
	 */
	private Klant retrieveKlant(RowSet rowSet) throws SQLException {
		Klant klant = new Klant();
		klant.setKlant_id(rowSet.getInt(1));
		klant.setVoornaam(rowSet.getString(2));
		klant.setTussenvoegsel(rowSet.getString(3));
		klant.setAchternaam(rowSet.getString(4));
		klant.setEmail(rowSet.getString(5));
		klant.setStraatnaam(rowSet.getString(6));
		klant.setHuisnummer(rowSet.getInt(7));
		klant.setToevoeging(rowSet.getString(8));
		klant.setPostcode(rowSet.getString(9));
		klant.setWoonplaats(rowSet.getString(10));

		return klant;
	}

	/**
	 * Retrieves bestelling data from rowSet and stores them in a Bestelling object. The bestelling
	 * data that is retrieved comes from the row to which the cursor in rowSet is currently 
	 * pointing. The method assumes rowSet contains data from all columns in the bestelling table.
	 * @return              the Bestelling object with data from the currently selected row in 
	 *                      rowSet
	 * @throws SQLException 
	 */
	private Bestelling retrieveBestelling(RowSet rowSet) throws SQLException {
		Bestelling bestelling = new Bestelling();
		bestelling.setBestelling_id(rowSet.getInt(1));
		bestelling.setKlant_id(rowSet.getInt(2));
		bestelling.setArtikel_id1(rowSet.getInt(3));
		bestelling.setArtikel_naam1(rowSet.getString(4));
		bestelling.setArtikel_aantal1(rowSet.getInt(5));
		bestelling.setArtikel_prijs1(rowSet.getDouble(6));
		bestelling.setArtikel_id2(rowSet.getInt(7));
		bestelling.setArtikel_naam2(rowSet.getString(8));
		bestelling.setArtikel_aantal2(rowSet.getInt(9));
		bestelling.setArtikel_prijs2(rowSet.getDouble(10));
		bestelling.setArtikel_id3(rowSet.getInt(11));
		bestelling.setArtikel_naam3(rowSet.getString(12));
		bestelling.setArtikel_aantal3(rowSet.getInt(13));
		bestelling.setArtikel_prijs3(rowSet.getDouble(14));

		return bestelling;
	}

	/**
	 * Updates the given klant in the database based on the klant id contained in the Klant object.
	 * If the klant id of the given klant is equal to 0, this method will not do anything, in which
	 * case it will return false. When klant data has been succesfully updated, this method will 
	 * return true.
	 * @param k                     klant data to be updated
	 * @return                      true if update was successful, false when nothing was updated
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public boolean update(Klant k) throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		if (k.getKlant_id() != 0) {
			executeCommand(SqlCodeGenerator.generateKlantUpdateCode(k));
			return true;
		}
		return false;
	}

	/**
	 * Updates the given bestelling in the database based on the bestelling id contained in the 
	 * Bestelling object. If the Bestelling id of the given bestelling is equal to 0, this method 
	 * will not do anything, in which case it will return false. When bestelling data has been 
	 * succesfully updated, this method will return true.
	 * @param b                     bestelling data to be updated
	 * @return                      true if update was successful, false when nothing was updated
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public boolean update(Bestelling b) throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		if (b.getBestelling_id() != 0) {
			executeCommand(SqlCodeGenerator.generateBestellingUpdateCode(b));
			return true;
		}
		return false;
	}

	/**
	 * Deletes the klant from the database with the given klant id.
	 * @param klant_id              the id of the klant that is to be deleted
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void deleteKlant(int klant_id) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		String klantSQL = "DELETE FROM klant WHERE klant_id = ?";
		String bestellingSQL = "DELETE FROM bestelling WHERE klant_id = ?";

		try (Connection con = dataSource.getConnection()) {
			boolean initialAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);

			try (PreparedStatement deleteKlant = con.prepareStatement(klantSQL);
					PreparedStatement deleteBestelling = con
							.prepareStatement(bestellingSQL)) {
				deleteKlant.setInt(1, klant_id);
				deleteKlant.executeUpdate();
				deleteBestelling.setInt(1, klant_id);
				deleteBestelling.executeUpdate();

				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw ex;
			} finally {
				con.setAutoCommit(initialAutoCommit);
			}
		}
	}

	/**
	 * Deletes the bestelling from the database with the given bestelling id.
	 * @param bestelling_id         the id of the bestelling that is to be deleted
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void deleteBestelling(int bestelling_id) throws SQLException,
			DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		executeCommand("DELETE FROM bestelling WHERE bestelling_id = "
				+ bestelling_id);
	}

	/**
	 * Deletes the given klant from the database based on voornaam, achternaam, and tussenvoegsel.
	 * @param k                     klant to be deleted
	 * @throws SQLException
	 * @throws DatabaseException    thrown if database connection has not been initialized yet
	 */
	public void delete(Klant k) throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		// Get information from klant object.
		String voornaam = k.getVoornaam();
		String achternaam = k.getAchternaam();
		String tussenvoegsel = k.getTussenvoegsel();

		// Retrieve all klant_ids from the database that have the specified voornaam, achternaam, 
		// and tussenvoegsel.
		ArrayList<Integer> klant_ids = new ArrayList<>();
		try (RowSet rowSet = createRowSet("SELECT klant_id FROM klant WHERE "
				+ "voornaam = '" + voornaam + "' AND " + "achternaam = '"
				+ achternaam + "' AND " + "tussenvoegsel = '" + tussenvoegsel
				+ "'")) {
			while (rowSet.next())
				klant_ids.add(rowSet.getInt(1));
		}

		// Declare connection object and prepare String for klant deletion.
		try (Connection con = dataSource.getConnection()) {
			boolean initialAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);

			String klantSQL = "DELETE FROM klant WHERE " + "voornaam = ? AND "
					+ "achternaam = ? AND " + "tussenvoegsel = ?";

			// Prepare String for deletion of all bestellingen that have the klant_ids of klanten
			// that will also be deleted.
			String bestellingSQL = "DELETE FROM bestelling WHERE klant_id = ?";
			;

			// Declare PreparedStatements.
			PreparedStatement[] deleteBestellingen = new PreparedStatement[klant_ids
					.size()];
			try (PreparedStatement deleteKlant = con.prepareStatement(klantSQL)) {
				// delete klant(en)
				deleteKlant.setString(1, voornaam);
				deleteKlant.setString(2, achternaam);
				deleteKlant.setString(3, tussenvoegsel);
				deleteKlant.executeUpdate();

				// delete bestelling(en)
				for (int i = 0; i < deleteBestellingen.length; i++) {
					deleteBestellingen[i] = con.prepareStatement(bestellingSQL);
					deleteBestellingen[i].setInt(1, klant_ids.get(i));
					deleteBestellingen[i].executeUpdate();
				}

				con.commit();
			} catch (SQLException ex) {
				con.rollback();
				throw ex;
			} finally {
				for (int i = 0; i < deleteBestellingen.length; i++)
					deleteBestellingen[i].close();
				con.setAutoCommit(initialAutoCommit);
			}
		}
	}

	/**
	 * Removes all data from the database. Tables still exist afterwards, except now they're empty.
	 * @throws SQLException
	 * @throws DatabaseException thrown if database connection has not been initialized yet
	 */
	public void clearDatabase() throws SQLException, DatabaseException {
		if (!isInitialized)
			throw new DatabaseException("Geen verbinding met database.");

		executeCommand("DELETE FROM klant");
		executeCommand("DELETE FROM bestelling");
	}

	/**
	 * Returns the type of DataSource this DatabaseConnector is using, or is set to use when 
	 * connecting to a database. The codes for DataSource types are defined by the constants 
	 * contained in this class.
	 * @return the code for the type of DataSource
	 */
	public byte getDataSourceType() {
		return dataSourceType;
	}

	/**
	 * Sets the type of DataSource this DatabaseConnector will use next time it initiates a 
	 * connection to a database. The codes for DataSource types are defined by the constants 
	 * contained in this class.
	 * @param dataSourceType the code for the type of DataSource to be used
	 * @throws DatabaseException thrown when parameter value is invalid
	 */
	/*public void setDataSourceType(byte dataSourceType) throws DatabaseException {
		if (dataSourceType < HIKARI_CP_DATASOURCE
				&& dataSourceType > C3P0_DATASOURCE)
			throw new DatabaseException("Instellen data source mislukt.");
		else {
			this.dataSourceType = dataSourceType;
		}
	}
*/
	/**
	 * Sets the driver that will be used to connect with SQL database.
	 * @param driver a String with driver name
	 */
	/*public void setDriver(String driver) {
		this.driver = driver;
	}
*/
	/**
	 * Return a String with the driver name currently stored in this DatabaseConnector instance.
	 * @return driver name
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * Return a String with the database url currently stored in this DatabaseConnector instance.
	 * @return database url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the database url that will be used to connect with SQL database.
	 * @param url a String with database url
	 */
	/*public void setUrl(String url) {
		this.url = url;
	}
*/
	/**
	 * Return a String with the username currently stored in this DatabaseConnector instance.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username that will be used to connect with SQL database.
	 * @param username a String with the username
	 */
	/*public void setUsername(String username) {
		this.username = username;
	}
*/
	/**
	 * Return a String with the password currently stored in this DatabaseConnector instance.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password that will be used to connect with SQL database.
	 * @param password a String with the password
	 */
	/*public void setPassword(String password) {
		this.password = password;
	}
*/
	/**
	 * Returns true when this DatabaseConnector instance is currently connected to a database.
	 * Returns false when this is not the case.
	 * @return true when connected, false when not connected
	 */
	public boolean isInitialized() {
		return isInitialized;
	}

	public static class Builder {
		private DataSource dataSource;
		private boolean isInitialized;
		private byte dataSourceType;
		private String driver;
		private String url;
		private String username;
		private String password;

		public Builder dataSource(DataSource dataSource) {
			this.dataSource = dataSource;
			return this;
		}

		public Builder isInitialized(boolean isInitialized) {
			this.isInitialized = isInitialized;
			return this;
		}

		public Builder dataSourceType(byte dataSourceType)throws DatabaseException {
			if (dataSourceType < HIKARI_CP_DATASOURCE
					&& dataSourceType > C3P0_DATASOURCE)
				throw new DatabaseException("Instellen data source mislukt.");
			else {
				this.dataSourceType = dataSourceType;
			//return this;
			}
			
			
			return this;
		}
		
		public Builder driver(String driver) {
			this.driver = driver;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public DatabaseConnector build() {
			return new DatabaseConnector(this);
		}
	}

	private DatabaseConnector(Builder builder) {
		this.dataSource = builder.dataSource;
		this.isInitialized = builder.isInitialized;
		this.dataSourceType = builder.dataSourceType;
		this.driver = builder.driver;
		this.url = builder.url;
		this.username = builder.username;
		this.password = builder.password;
	}
}
