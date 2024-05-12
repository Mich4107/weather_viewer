package ru.yuubi.weather_viewer.service.cleanup;

import java.util.Timer;

public class DatabaseScheduler {
    private Timer timer;
    private DatabaseCleanupService cleanupService;

    public DatabaseScheduler(DatabaseCleanupService cleanupService) {
        this.cleanupService = cleanupService;
        this.timer = new Timer();
    }

    public void startCleanupTask(long delay, long period){
        timer.schedule(new DatabaseCleanupTask(cleanupService), delay, period);
    }

}
