package ru.yuubi.weather_viewer.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.utils.HibernateUtil;

public class UserDAO {

    public User getUserByLoginAndPassword(String login, String password){
        User user;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            String hql = "from User where login = :login and password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            query.setParameter("password", password);
            user = (User) query.uniqueResult();
            session.getTransaction().commit();
        }
        return user;
    }

    public User getUserById(int id) {
        User user;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
        }
        return user;
    }

    public User getUserByLogin(String login) {
        User user;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            String hql = "from User where login =: login";
            user = (User) session.createQuery(hql).setParameter("login", login).uniqueResult();
            session.getTransaction().commit();
        }
        return user;
    }
    public void save(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}
