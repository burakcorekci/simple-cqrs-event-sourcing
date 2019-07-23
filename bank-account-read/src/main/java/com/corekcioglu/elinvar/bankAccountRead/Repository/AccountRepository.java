package com.corekcioglu.elinvar.bankAccountRead.Repository;

import com.corekcioglu.elinvar.bankAccountRead.Entity.Account;
import com.corekcioglu.elinvar.bankAccountRead.Repository.Generic.JpaGenericDAO;
import com.google.inject.persist.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class AccountRepository extends JpaGenericDAO<Account, UUID> {
    @Transactional
    public Account findByEmail(String email) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.email = :email",
                Account.class
        );
        query.setParameter("email", email);

        Account account = null;
        try {
            account = query.getSingleResult();
        }
        catch (Exception e) {

        }

        return account;
    }

    @Transactional
    public List<Account> findByName(String name) {
        TypedQuery<Account> query = entityManager.createQuery(
                "SELECT a FROM Account a WHERE a.name = :name",
                Account.class
        );
        query.setParameter("name", name);
        return query.getResultList();
    }
}
