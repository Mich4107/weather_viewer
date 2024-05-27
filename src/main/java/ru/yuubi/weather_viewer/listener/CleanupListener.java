package ru.yuubi.weather_viewer.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.yuubi.weather_viewer.service.cleanup.CleanupService;

import java.util.concurrent.TimeUnit;

@WebListener
public class CleanupListener implements ServletContextListener {
    private CleanupService cleanupService = new CleanupService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        cleanupService.startCleanup(1, TimeUnit.DAYS);
    }
}
