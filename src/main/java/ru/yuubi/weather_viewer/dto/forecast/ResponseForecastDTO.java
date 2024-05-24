package ru.yuubi.weather_viewer.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ResponseForecastDTO {
    private double temp;
    private String description;
    private String iconId;
    private String time;
    private String name;
}
