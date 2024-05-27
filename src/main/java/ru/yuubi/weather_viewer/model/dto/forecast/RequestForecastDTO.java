package ru.yuubi.weather_viewer.model.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class RequestForecastDTO {
    private String temp;
    private String description;
    private String iconUrl;
    private LocalDateTime time;
    private String name;

    private String dayTemp;
    private String nightTemp;

    public RequestForecastDTO(String temp, String description, String iconUrl,
                              LocalDateTime time, String name) {
        this.temp = temp;
        this.description = description;
        this.iconUrl = iconUrl;
        this.time = time;
        this.name = name;
    }

    public RequestForecastDTO(String description, String iconUrl, LocalDateTime time, String name, String dayTemp, String nightTemp) {
        this.description = description;
        this.iconUrl = iconUrl;
        this.time = time;
        this.name = name;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
    }
}
