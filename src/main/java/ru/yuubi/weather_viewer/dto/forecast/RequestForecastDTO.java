package ru.yuubi.weather_viewer.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestForecastDTO {
    private String temp;
    private String description;
    private String iconUrl;
    private LocalDateTime time;
    private String name;

    public RequestForecastDTO(String temp, String description, String iconUrl,
                              LocalDateTime time, String name) {
        this.temp = temp;
        this.description = description;
        this.iconUrl = iconUrl;
        this.time = time;
        this.name = name;
    }
}
