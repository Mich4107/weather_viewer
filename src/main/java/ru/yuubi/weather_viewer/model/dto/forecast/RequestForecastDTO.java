package ru.yuubi.weather_viewer.model.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RequestForecastDTO {
    private String temp;
    private String description;
    private String iconUrl;
    private LocalDateTime time;
    private String name;
}
