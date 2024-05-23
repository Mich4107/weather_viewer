package ru.yuubi.weather_viewer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RequestWeatherDTO {
    private int locationId;
    private double lat;
    private double lon;
    private String locationInfo;
    private String weatherDescription;
    private String temp;
    private String tempFeelsLike;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String iconUrl;
    public RequestWeatherDTO(String locationInfo, String weatherDescription, String temp,
                             String tempFeelsLike, String pressure, String humidity,
                             String windSpeed, String iconUrl, double lat, double lon) {
        this.locationInfo = locationInfo;
        this.weatherDescription = weatherDescription;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.iconUrl = iconUrl;
        this.lat = lat;
        this.lon = lon;
    }
}
