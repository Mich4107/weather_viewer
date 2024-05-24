package ru.yuubi.weather_viewer.dto.weather;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWeatherDTO {
    private double longitude;
    private double latitude;
    private String description;
    private int temp;
    private int tempFeelsLike;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private String locationName;
    private String countryCode;
    private String iconId;
}

