package ru.yuubi.weather_viewer.exception;

public class WeatherAPIException extends RuntimeException{
    public WeatherAPIException(String message) {
        super(message);
    }
}
