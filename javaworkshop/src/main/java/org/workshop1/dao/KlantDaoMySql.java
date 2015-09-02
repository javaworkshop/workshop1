package org.workshop1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.workshop1.model.Klant;
import org.workshop1.model.Adres;

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

    private Connection connection;

    // Constructors -------------------------------------------------------------------------------

    KlantDaoMySql(Connection con) {
        connection = con;
    }

    // Actions ------------------------------------------------------------------------------------
    
    @Override
    public Klant read(int id) throws DaoException {
        return read(SQL_FIND_BY_ID, id).get(0);
    }
    
    
    @Override
    public  ArrayList<Klant> read(String voornaam, String achternaam) throws DaoException {
        return read(SQL_FIND_BY_FIRSTNAME_LASTNAME, voornaam, achternaam);
    }
    
    @Override
    public  ArrayList<Klant> read(String voornaam) throws DaoException {
        return read(SQL_FIND_BY_FIRSTNAME, voornaam);
    }

    @Override
    public ArrayList<Klant> read(Adres adres) throws DaoException {
        return read(SQL_FIND_BY_ADDRESS, 
            adres.getStraatnaam(),
            adres.getHuisnummer(),
            adres.getToevoeging(),
            adres.getPostcode(),
            adres.getWoonplaats());
    }

    @Override
    public ArrayList<Klant> read(String postcode, int huisnummer) throws DaoException {
        return read(SQL_FIND_BY_POSTALCODE_HOUSENUMBER, postcode, huisnummer);
    }
 
    private ArrayList<Klant> read(String sql, Object... values) throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();

        try (
            PreparedStatement statement = prepareStatement(connection, sql, false, values);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while(resultSet.next()) {
                klanten.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return klanten;
    }

 
    @Override
    public ArrayList<Klant> readAll() throws DaoException {
        ArrayList<Klant> klanten = new ArrayList<>();

        try (
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                klanten.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return klanten;
    }
    

    @Override
    public void add(Klant klant) throws IllegalArgumentException, DaoException {
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
            PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    klant.setKlant_id(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Klant klant) throws DaoException {
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
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Klant klant) throws DaoException {
        delete(klant.getKlant_id());
    }
    
    @Override
    public void delete(int id) throws DaoException {
        Object[] values = { 
            id
        };

        try (
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
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

    private static PreparedStatement prepareStatement
        (Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
            throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement(sql,
            returnGeneratedKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
        setValues(statement, values);
        return statement;
    }
        
    private static void setValues(PreparedStatement statement, Object... values)
        throws SQLException
    {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }
    
    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        }
        catch(SQLException ex) {
            throw new DaoException("Failed to close KlantDao.", ex);
        }
    }

}