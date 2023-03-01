package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String dbURL = "jdbc:mysql://localhost:3306/data";
    private final String userName = "roma";
    private final String password = "88888888";

    private static SessionFactory sessionFactory;

    public Connection getConnection() throws SQLException {
       return DriverManager.getConnection(dbURL, userName, password);
    }

    public SessionFactory getSessionConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        Configuration cfg = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.order_updates", "true")
                .setProperty("hibernate.connection.driver_class", driver)
                .setProperty("hibernate.connection.url", dbURL)
                .setProperty("hibernate.connection.username", userName)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();

        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
}


