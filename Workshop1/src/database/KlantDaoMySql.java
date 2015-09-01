
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Klant;

/**
 *
 * @author gerbrich2
 */
public class KlantDaoMySql implements KlantDao{
    
    // Constants ----------------------------------------------------------------------------------

    private static final String SQL_FIND_BY_FIRSTNAME_LASTNAME =
        "SELECT * FROM Klant WHERE voornaam = ?, achternaam = ?";
    private static final String SQL_FIND_BY_FIRSTNAME =
        "SELECT * FROM Klant WHERE voornaam = ?";
    private static final String SQL_FIND_BY_ADDRESS =
        "SELECT * FROM Klant WHERE straatnaam = ?, huisnummer = ?, toevoeging = ?, postcode = ?, plaatsnaam = ?";
    private static final String SQL_FIND_BY_POSTALCODE_HOUSENUMBER =
        "SELECT * FROM Klant WHERE postcode = ?, huisnummer = ?";    
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM Klant WHERE klant_id = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
        "SELECT * FROM Klant ORDER BY klant_id";
    private static final String SQL_INSERT =
        "INSERT INTO Klant (voornaam, tussenvoegsel, achternaam, email, straatnaam, huisnummer, toevoeging, postcode, woonplaats)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE Klant SET voornaam = ?, tussenvoegsel = ?, achternaam = ?, email = ?, straatnaam = ?, huisnummer = ?, toevoeging = ?, postcode = ?, woonplaats = ?"
            + " WHERE klant_id = ?";
    private static final String SQL_DELETE =
        "DELETE FROM Klant WHERE klant_id = ?";

    // Vars ---------------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    KlantDaoMySql(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    
    @Override
    public Klant read(int id) throws DAOException {
        return read(SQL_FIND_BY_ID, id);
    }
    
    
    @Override
    public Klant read(String voornaam, String achternaam) throws DAOException {
        return read(SQL_FIND_BY_FIRSTNAME_LASTNAME, voornaam, achternaam);
    }
    
    @Override
    public Klant read(String voornaam) throws DAOException {
        return read(SQL_FIND_BY_FIRSTNAME, voornaam);
    }

    @Override
    public Klant read(Adres adres) throws DAOException {
        return read(SQL_FIND_BY_ADDRESS, 
            adres.getStraatnaam(),
            adres.getHuisnummer(),
            adres.getToevoeging(),
            adres.getPostcode(),
            adres.getWoonplaats());
    }

    @Override
    public Klant read(String postcode, int huisnummer) throws DAOException {
        return read(SQL_FIND_BY_POSTALCODE_HOUSENUMBER, postcode, huisnummer);
    }
 
    private Klant read(String sql, Object... values) throws DAOException {
        Klant klant = null;

        try (
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = prepareStatement(connection, sql, false, values);
            ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                klant = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return klant;
    }

 
    @Override
    public ArrayList<Klant> readAll() throws DAOException {
        ArrayList<Klant> klanten = new ArrayList<>();

        try (
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                klanten.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return klanten;
    }
    

    @Override
    public void add(Klant klant) throws IllegalArgumentException, DAOException {
        if (klant.getKlant_id() != 0) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
            klant.getVoornaam(),
            klant.getTussenvoegsel(),
            klant.getAchternaam(),
            klant.getEmail(),
            klant.getStraatnaam(),
            klant.getHuisnummer(),
            klant.getToevoeging(),
            klant.getPostcode(),
            klant.getWoonplaats()
        };

        try (
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    klant.setKlant_id(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Klant klant) throws DAOException {
        if (klant.getKlant_id() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
            klant.getVoornaam(),
            klant.getTussenvoegsel(),
            klant.getAchternaam(),
            klant.getEmail(),
            klant.getStraatnaam(),
            klant.getHuisnummer(),
            klant.getToevoeging(),
            klant.getPostcode(),
            klant.getWoonplaats()
        };

        try (
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Klant klant) throws DAOException {
        Object[] values = { 
            klant.getKlant_id()
        };

        try (
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                klant.setKlant_id(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    
    
    // Helpers ------------------------------------------------------------------------------------
    
    private static Klant map(ResultSet resultSet) throws SQLException {
        Klant klant = new Klant();
        
        klant.setVoornaam(resultSet.getString("voornaam"));
        klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
        klant.setAchternaam(resultSet.getString("achternaam"));
        klant.setEmail(resultSet.getString("email"));
        klant.setStraatnaam(resultSet.getString("straatnaam"));
        klant.setHuisnummer(resultSet.getInt("huisnummer"));
        klant.setToevoeging(resultSet.getString("toevoeging"));
        klant.setPostcode(resultSet.getString("postcode"));
        klant.setWoonplaats(resultSet.getString("woonplaats"));
        
        return klant;
    }

    public static PreparedStatement prepareStatement
        (Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
            throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql,
            returnGeneratedKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
        setValues(statement, values);
        return statement;
    }
        
    public static void setValues(PreparedStatement statement, Object... values)
        throws SQLException
    {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }

}
