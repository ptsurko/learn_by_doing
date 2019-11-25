package com.test.api.dao;

import com.test.api.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDao {
    public User find(Long id) {
        try (Session session = SessionObj.getInstance().getSession()) {
            return session.get(User.class, id);
        }
    }

    public List<User> findAll() {
        try (Session session = SessionObj.getInstance().getSession()) {
            Query<User> query = session.createQuery("FROM User ORDER BY id DESC", User.class);
            return query.getResultList();
        }
    }

    public User add(User entity) {
        Session session = SessionObj.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return entity;
    }
}
