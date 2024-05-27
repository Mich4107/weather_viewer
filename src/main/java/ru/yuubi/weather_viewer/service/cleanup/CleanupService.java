package ru.yuubi.weather_viewer.service.cleanup;

import ru.yuubi.weather_viewer.dao.SessionDAO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupService {
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private SessionDAO sessionDAO = new SessionDAO();

    public void startCleanup(long delay, TimeUnit unit){
        executorService.scheduleAtFixedRate(() -> sessionDAO.removeExpiredSessions(), 0, delay, unit);
    }
}
