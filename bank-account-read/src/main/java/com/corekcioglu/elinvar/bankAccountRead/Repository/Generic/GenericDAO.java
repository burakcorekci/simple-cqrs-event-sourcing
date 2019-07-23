package com.corekcioglu.elinvar.bankAccountRead.Repository.Generic;

import com.corekcioglu.elinvar.bankAccountRead.Entity.AbstractEntity;

import java.util.List;

public interface GenericDAO<E extends AbstractEntity, P> {
    E create(E entity);
    E update(E entity);
    E findById(P id);
    void delete(E entity);
    List<E> findAll();
}
