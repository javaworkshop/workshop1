package org.workshop1.dao;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.workshop1.model.Klant;

@Repository("genericKlantDao")
@Transactional
public class GenericKlantDaoJpa extends GenericDaoJpa<Klant, Integer> implements GenericKlantDao {
    @Override
    public List<Klant> findByNaam(String voornaam, String tussenvoegsel, String achternaam) {
        Query query = entityManager.createNamedQuery("Klant.byNaam");
        // Alternative:
        /*
        Query query = entityManager.createQuery("from klant where voornaam = ? and tussenvoegsel = ?
                and achternaam = ?");
        */
        query.setParameter(1, voornaam);
        query.setParameter(2, tussenvoegsel);
        query.setParameter(3, achternaam);
        return query.getResultList();
    }    
}
