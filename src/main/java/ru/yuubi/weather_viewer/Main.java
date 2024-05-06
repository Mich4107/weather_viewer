package ru.yuubi.weather_viewer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.yuubi.weather_viewer.entity.User;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try(factory) {
            Session session = factory.getCurrentSession();
            User user = new User("Yuubi", "123");
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}
