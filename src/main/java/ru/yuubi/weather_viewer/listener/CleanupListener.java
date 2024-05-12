package ru.yuubi.weather_viewer.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.yuubi.weather_viewer.service.cleanup.DatabaseCleanupService;
import ru.yuubi.weather_viewer.service.cleanup.DatabaseScheduler;

@WebListener
public class CleanupListener implements ServletContextListener {
    private DatabaseCleanupService service = new DatabaseCleanupService();
    private DatabaseScheduler scheduler = new DatabaseScheduler(service);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler.startCleanupTask(0, 1000*60*60*24);
    }
}
