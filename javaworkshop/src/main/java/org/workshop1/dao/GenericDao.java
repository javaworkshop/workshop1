package org.workshop1.dao;

// Generics: E is the entity class being modeled, PK is the class that represents the primary key of
// the entity.
public interface GenericDao<E, PK> {
    public void add(E entity);
    public void delete(E entity);
    public E update(E entity);
    public E readById(PK id);
}
