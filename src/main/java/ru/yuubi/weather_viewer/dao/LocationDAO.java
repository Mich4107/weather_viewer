package ru.yuubi.weather_viewer.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.utils.HibernateUtil;

import java.util.List;

public class LocationDAO {
    private SessionFactory sessionFactory;

    public void saveLocation(Location location) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();
            session.persist(location);
            session.getTransaction().commit();
        }
    }

    public List<Location> getLocationsByUserId(int id) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();

            String hql = "from Location where userId = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List<Location> locations = query.getResultList();

            session.getTransaction().commit();

            return locations;
        }
    }

    public Location getLocationByLatAndLon(double lat, double lon, int userId) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();

            String hql = "from Location where latitude = :lat AND longitude = :lon AND userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("lat", lat);
            query.setParameter("lon", lon);
            query.setParameter("userId", userId);

            Location location = (Location) query.uniqueResult();

            session.getTransaction().commit();

            return location;
        }
    }

    public void deleteLocation(int id) {
        Session session = sessionFactory.getCurrentSession();
        try(session) {
            session.beginTransaction();

            String hql = "delete Location where id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    public LocationDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public LocationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
