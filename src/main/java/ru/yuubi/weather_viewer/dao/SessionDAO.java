package ru.yuubi.weather_viewer.dao;

import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.model.Session;
import ru.yuubi.weather_viewer.utils.HibernateUtil;

import java.time.LocalDateTime;

public class SessionDAO {


    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void remove(String GUID) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();

        try(session) {
            session.beginTransaction();

            String hql = "delete from Session where id = :GUID";
            Query query = session.createQuery(hql);
            query.setParameter("GUID", GUID);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    public void removeExpiredSessions(){
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();

            LocalDateTime localDateTime = LocalDateTime.now();
            String hql = "delete from Session where expiresAt < :localDateTime";
            Query query = session.createQuery(hql);
            query.setParameter("localDateTime", localDateTime);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    public Session findByGUID(String GUID) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        Session sessionEntity;
        try(session) {
            session.beginTransaction();
            sessionEntity = session.get(Session.class, GUID);
            session.getTransaction().commit();
        }
        return sessionEntity;
    }

    public void save(Session sessionEntity) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.persist(sessionEntity);
            session.getTransaction().commit();
        }
    }
    public void update(Session sessionEntity) {
        org.hibernate.Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.merge(sessionEntity);
            session.getTransaction().commit();
        }
    }

    public SessionDAO() {
    }

    public SessionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
