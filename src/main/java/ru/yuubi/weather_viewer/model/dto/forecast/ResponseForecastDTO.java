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
public class ResponseForecastDTO {
    private double temp;
    private String description;
    private String iconId;
    private String time;
    private String name;

    private Double dayTemp;
    private Double nightTemp;

    public ResponseForecastDTO(double temp, String description, String iconId, String time, String name) {
        this.temp = temp;
        this.description = description;
        this.iconId = iconId;
        this.time = time;
        this.name = name;
    }

    public ResponseForecastDTO(String description, String iconId, String time, String name, Double dayTemp, Double nightTemp) {
        this.description = description;
        this.iconId = iconId;
        this.time = time;
        this.name = name;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
    }
}
