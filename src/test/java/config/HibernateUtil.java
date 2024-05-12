package config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.entity.User;

import java.util.Properties;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null) {
            try{
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.JAKARTA_JDBC_DRIVER, "org.h2.Driver");
                settings.put(Environment.JAKARTA_JDBC_URL, "jdbc:h2:mem:testdb");
                settings.put(Environment.JAKARTA_JDBC_USER, "yuubi1");
                settings.put(Environment.JAKARTA_JDBC_PASSWORD, "yuubi1");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Location.class);
                configuration.addAnnotatedClass(SessionEntity.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                System.out.println("~~~H2DB config~~~");
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                return sessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
