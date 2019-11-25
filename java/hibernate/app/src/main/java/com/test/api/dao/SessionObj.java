package com.test.api.dao;

import com.test.api.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionObj {
    private static SessionObj instance=new SessionObj();
    private SessionFactory sessionFactory;

    public static SessionObj getInstance(){
        return instance;
    }

    private SessionObj(){
        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class);
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        config.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
        config.setProperty("hibernate.connection.username", System.getenv("DATABASE_USER"));
        config.setProperty("hibernate.connection.password", System.getenv("DATABASE_PASSWORD"));
        config.setProperty("hibernate.hbm2ddl.auto", "update");

        sessionFactory = config.buildSessionFactory();
    }

    public static Session getSession(){
        return getInstance().sessionFactory.openSession();
    }
}
