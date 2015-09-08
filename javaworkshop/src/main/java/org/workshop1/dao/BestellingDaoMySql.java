
package org.workshop1.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.workshop1.model.Bestelling;

public class BestellingDaoMySql implements BestellingDao {
    
    private static final String SQL_FIND_BY_ID = 
            "SELECT * FROM Bestelling WHERE bestelling_id = ?";
    private static final String SQL_INSERT = 
            "INSERT INTO Bestelling"
            + " (klant_id, artikel_id1, artikel_id2, artikel_id3, artikel_naam1, artikel_naam2, artikel_naam3,"
            + " artikel_aantal1, artikel_aantal2, artikel_aantal3, artikel_prijs1, artikel_prijs2, artikel_prijs3)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM Bestelling WHERE bestelling_id = ?";
    private static final String SQL_UPDATE = 
            "UPDATE Bestelling"
            + "SET klant_id = ?, artikel_id1 = ?, artikel_id2 = ?, artikel_id3 = ?, artikel_naam1 = ?, artikel_naam2 = ?, artikel_naam3 = ?,"
            + " artikel_aantal1 = ?, artikel_aantal2 = ?, artikel_aantal3 = ?, artikel_prijs1 = ?, artikel_prijs2 = ?, artikel_prijs3 = ?"
            + " WHERE bestelling_id = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
        "SELECT * FROM Bestelling ORDER BY bestelling_id";

    private Connection connection;
    
    BestellingDaoMySql(Connection con) {
        connection = con;
    }
    
    @Override
    public Bestelling read(int bestelling_id) throws DaoException {
    Bestelling bestelling = null;

    try (
        PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, bestelling_id);
        ResultSet resultSet = statement.executeQuery();
    ) {
        if (resultSet.next()) {
            bestelling = map(resultSet);
        }
    } catch (SQLException e) {
        throw new DaoException(e);
    }

    return bestelling;
    }

    @Override
    public ArrayList<Bestelling> readAll() throws DaoException {
        ArrayList<Bestelling> bestellingen = new ArrayList<>();

        try (
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                bestellingen.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return bestellingen;    }

    @Override
    public void add(Bestelling bestelling) throws DaoException {
        if (bestelling.getBestelling_id() != 0) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
            bestelling.getKlant_id(),
            bestelling.getArtikel_id1(),
            bestelling.getArtikel_id2(),
            bestelling.getArtikel_id3(),
            bestelling.getArtikel_naam1(),
            bestelling.getArtikel_naam2(),
            bestelling.getArtikel_naam3(),
            bestelling.getArtikel_aantal1(),
            bestelling.getArtikel_aantal2(),
            bestelling.getArtikel_aantal3(),
            bestelling.getArtikel_prijs1(),
            bestelling.getArtikel_prijs2(),
            bestelling.getArtikel_prijs3()
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
                    bestelling.setBestelling_id(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Bestelling bestelling) throws DaoException {
        if (bestelling.getBestelling_id() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
            bestelling.getKlant_id(),
            bestelling.getArtikel_id1(),
            bestelling.getArtikel_id2(),
            bestelling.getArtikel_id3(),
            bestelling.getArtikel_naam1(),
            bestelling.getArtikel_naam2(),
            bestelling.getArtikel_naam3(),
            bestelling.getArtikel_aantal1(),
            bestelling.getArtikel_aantal2(),
            bestelling.getArtikel_aantal3(),
            bestelling.getArtikel_prijs1(),
            bestelling.getArtikel_prijs2(),
            bestelling.getArtikel_prijs3()
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
        }    }

    @Override
    public void delete(Bestelling bestelling) throws DaoException {
        delete(bestelling.getBestelling_id());
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
    
    private static PreparedStatement prepareStatement(
            Connection connection, String SQL, boolean returnGeneratedKeys, Object...values) throws SQLException{
        
        PreparedStatement  statement = connection.prepareStatement(SQL,
                returnGeneratedKeys ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
        setValues(statement, values);
        return statement;
    }
    
    private static void setValues(PreparedStatement statement, Object...values) throws SQLException{
        for (int i = 0; i < values.length; i++){
            statement.setObject(i + 1,values[i]);
        }
    }
    
    private Bestelling map (ResultSet resultSet) throws SQLException{
        Bestelling bestelling = new Bestelling();
        
        bestelling.setBestelling_id(resultSet.getInt("bestelling_id"));
        bestelling.setKlant_id(resultSet.getInt("klant_id"));
        bestelling.setArtikel_id1(resultSet.getInt("artikel_id1"));
        bestelling.setArtikel_id2(resultSet.getInt("artikel_id2"));
        bestelling.setArtikel_id3(resultSet.getInt("artikel_id3"));
        bestelling.setArtikel_naam1(resultSet.getString("artikel_naam1"));
        bestelling.setArtikel_naam2(resultSet.getString("artikel_naam2"));
        bestelling.setArtikel_naam3(resultSet.getString("artikel_naam3"));
        bestelling.setArtikel_aantal1(resultSet.getInt("artikel_aantal1"));
        bestelling.setArtikel_aantal2(resultSet.getInt("artikel_aantal2"));
        bestelling.setArtikel_aantal3(resultSet.getInt("artikel_aantal3"));
        bestelling.setArtikel_prijs1(resultSet.getDouble("artikel_prijs1"));
        bestelling.setArtikel_prijs2(resultSet.getDouble("artikel_prijs2"));
        bestelling.setArtikel_prijs3(resultSet.getDouble("artikel_prijs3"));
        
        return bestelling;
    }
    
    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        }
        catch(SQLException ex) {
            throw new DaoException("Failed to close BestellingDao.", ex);
        }
    }
    
}