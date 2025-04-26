package org.example.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.example.bean.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Util {
    final String URL = "jdbc:mysql://localhost:3306/test";
    final String USER_NAME = "root";
    final String PASSWORD = "root";
    SessionFactory sessionFactory;

    public Connection getJDBCConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            System.out.println("Соединение не установлено");
            throw new RuntimeException(e);
        }
    }

    public SessionFactory getHibernateConnection() {
        if (Objects.isNull(sessionFactory)) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, USER_NAME);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "create");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public SessionFactory getSessionFactoryProperties() {
        if (Objects.isNull(sessionFactory)) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        if (Objects.isNull(sessionFactory)) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}