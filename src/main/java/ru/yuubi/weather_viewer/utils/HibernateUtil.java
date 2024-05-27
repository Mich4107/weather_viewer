package ru.yuubi.weather_viewer.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.yuubi.weather_viewer.model.Location;
import ru.yuubi.weather_viewer.model.SessionEntity;
import ru.yuubi.weather_viewer.model.User;

import java.util.Properties;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null) {
            try{
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/weather_viewer?useSSL=false");
                settings.put(Environment.JAKARTA_JDBC_USER, "yuubi1");
                settings.put(Environment.JAKARTA_JDBC_PASSWORD, "yuubi1");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Location.class);
                configuration.addAnnotatedClass(SessionEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                System.out.println("Hibernate Java Config serviceRegistry created");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
