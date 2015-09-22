package org.workshop1.dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class GenericDaoJpa<E, PK> implements GenericDao<E, PK> {
    protected Class entityClass;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    public GenericDaoJpa() {
        ParameterizedType genericSuperClass = (ParameterizedType)getClass().getGenericSuperclass();
        entityClass = (Class)genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public void add(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public E update(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public E readById(PK id) {
        return (E)entityManager.find(entityClass, id);
    }
    
    
}
