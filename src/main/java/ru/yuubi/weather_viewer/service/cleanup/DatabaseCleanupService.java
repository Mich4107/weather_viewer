package ru.yuubi.weather_viewer.service.cleanup;

import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.SessionDAO;

public class DatabaseCleanupService {
    private SessionDAO sessionDAO;
    public void cleanupDataBase() {
        sessionDAO.removeExpiredSessions();
    }

    public DatabaseCleanupService() {
        sessionDAO = new SessionDAO();
    }

    public DatabaseCleanupService(SessionFactory sessionFactory) {
        sessionDAO = new SessionDAO(sessionFactory);
    }
}
