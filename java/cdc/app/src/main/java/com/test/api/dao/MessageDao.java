package com.test.api.dao;

import com.test.api.models.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class MessageDao {
    public Message find(Long id) {
        try (Session session = SessionObj.getInstance().getSession()) {
            return session.get(Message.class, id);
        }
    }

    public List<Message> findAll() {
        try (Session session = SessionObj.getInstance().getSession()) {
            Query<Message> query = session.createQuery("FROM Message ORDER BY id DESC", Message.class);
            return query.getResultList();
        }
    }

    public Message add(Message entity) {
        Session session = SessionObj.getInstance().getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            entity.setCreatedAt(Timestamp.from(Instant.now()));
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
