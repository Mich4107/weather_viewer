package ru.yuubi.weather_viewer.service.cleanup;

import java.util.TimerTask;

public class DatabaseCleanupTask extends TimerTask {
    private DatabaseCleanupService cleanupService;

    public DatabaseCleanupTask(DatabaseCleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @Override
    public void run() {
        cleanupService.cleanupDataBase();
    }
}
