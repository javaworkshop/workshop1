package org.workshop1.dao.test;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.workshop1.dao.test.config.TestConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.workshop1.dao.GenericKlantDao;
import org.workshop1.dao.test.service.SampleService;
import org.workshop1.model.Klant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfigurator.class},
        loader=AnnotationConfigContextLoader.class)
public class GenericKlantDaoTest {
    @Autowired
    SampleService sampleService;
    @Autowired
    @Qualifier("genericKlantDao")
    GenericKlantDao klantDao;    
    
    @Test
    public void testAdd() {
        klantDao.add(sampleService.getKlant(0));
        Klant k = klantDao.readById(1);
        assertEquals(k, sampleService.getKlant(0));
    }
    
    @Test
    public void testFindByNaam() {
        klantDao.add(sampleService.getKlant(1));
        List<Klant> kList = klantDao.findByNaam("Piet", "de", "Prutser");
        assertEquals(kList.get(0), sampleService.getKlant(1));
    }
}
