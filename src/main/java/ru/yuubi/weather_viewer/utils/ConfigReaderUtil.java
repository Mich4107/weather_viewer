package ru.yuubi.weather_viewer.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReaderUtil {
    private static Properties props = new Properties();
    public static String getApiKey(){
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty("api.key");
    }
}
