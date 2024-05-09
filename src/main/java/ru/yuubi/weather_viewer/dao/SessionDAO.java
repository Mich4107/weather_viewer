package ru.yuubi.weather_viewer.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.utils.HibernateUtil;

public class SessionDAO {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void removeSession(String GUID) {
        Session session = sessionFactory.getCurrentSession();

        try(session) {
            session.beginTransaction();

            String hql = "delete from SessionEntity where id = :GUID";
            Query query = session.createQuery(hql);
            query.setParameter("GUID", GUID);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    public SessionEntity getSessionEntity(String GUID) {
        Session session = sessionFactory.getCurrentSession();
        SessionEntity sessionEntity;
        try(session) {
            session.beginTransaction();
            sessionEntity = session.get(SessionEntity.class, GUID);
            session.getTransaction().commit();
        }
        return sessionEntity;
    }
    public void save(SessionEntity sessionEntity) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.persist(sessionEntity);
            session.getTransaction().commit();
        }
    }

    public void update(SessionEntity sessionEntity) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.merge(sessionEntity);
            session.getTransaction().commit();
        }
    }
}
