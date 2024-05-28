package ru.yuubi.weather_viewer.model.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseForecastDTO {
    private double temp;
    private String description;
    private String iconId;
    private String time;
    private String name;
}
