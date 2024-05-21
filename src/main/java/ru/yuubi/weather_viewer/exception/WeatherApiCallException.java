package ru.yuubi.weather_viewer.exception;

public class WeatherApiCallException extends RuntimeException{
    public WeatherApiCallException(String message) {
        super(message);
    }
}
