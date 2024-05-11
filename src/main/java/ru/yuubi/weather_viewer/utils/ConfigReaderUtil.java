package ru.yuubi.weather_viewer.utils;

import ru.yuubi.weather_viewer.service.AuthService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReaderUtil {
    private static Properties props = new Properties();
    public static String getApiKey() {

        InputStream inputStream = ConfigReaderUtil.class.getResourceAsStream("/config.properties");
        try (inputStream) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: config.properties");
            }
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty("api.key");
    }
}
