package ru.yuubi.weather_viewer.model.dto.weather;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
