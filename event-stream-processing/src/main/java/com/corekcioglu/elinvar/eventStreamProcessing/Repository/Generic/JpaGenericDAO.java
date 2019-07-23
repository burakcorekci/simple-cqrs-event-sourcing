package com.corekcioglu.elinvar.eventStreamProcessing.Repository.Generic;

import com.corekcioglu.elinvar.eventStreamProcessing.Entity.AbstractEntity;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class JpaGenericDAO<E extends AbstractEntity, P> implements GenericDAO<E, P> {
    @Inject
    protected EntityManager entityManager;

    private final Class<E> entityClass;

    public JpaGenericDAO() {
        // Use reflection to get the entity class
        // Transactional annotation from guice creates a new version of the class,
        // therefore we need the getSuperclass before getGenericSuperclass.
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Transactional
    @Override
    public E create(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public E update(E entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    @Override
    public E findById(P id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Transactional
    @Override
    public List<E> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> rootEntry = cq.from(entityClass);
        CriteriaQuery<E> all = cq.select(rootEntry);
        TypedQuery<E> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
