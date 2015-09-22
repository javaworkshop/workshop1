package org.workshop1.dao;

import java.util.List;
import org.workshop1.model.Klant;

public interface GenericKlantDao extends GenericDao<Klant, Integer> {
    public List<Klant> findByNaam(String voornaam, String tussenvoegsel, String achternaam);
}
