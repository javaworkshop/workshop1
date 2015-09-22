package org.workshop1.dao.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.workshop1.dao.GenericDao;
import org.workshop1.dao.GenericDaoJpa;
import org.workshop1.dao.GenericKlantDao;
import org.workshop1.dao.GenericKlantDaoJpa;
import org.workshop1.dao.test.service.SampleService;
import org.workshop1.dao.test.service.SampleServiceImpl;

@Configuration
@ImportResource("classpath:TestContext.xml")
@ComponentScan(basePackageClasses = {GenericDao.class, GenericDaoJpa.class, GenericKlantDao.class,
    GenericKlantDaoJpa.class})
public class TestConfigurator {
    @Bean
    public SampleService createSampleService() {
        return new SampleServiceImpl();
    }
    
    @Bean
    public GenericKlantDao genericKlantDao() {
        return new GenericKlantDaoJpa();
    }
}
