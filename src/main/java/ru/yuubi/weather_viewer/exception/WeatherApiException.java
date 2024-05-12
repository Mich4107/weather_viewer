package ru.yuubi.weather_viewer.exception;

public class WeatherApiException extends RuntimeException{
    public WeatherApiException(String message) {
        super(message);
    }
}
