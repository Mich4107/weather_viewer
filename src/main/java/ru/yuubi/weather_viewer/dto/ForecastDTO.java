package ru.yuubi.weather_viewer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ForecastDTO {
    private String temp;
    private String description;
    private String iconUrl;
    private LocalDateTime time;
    private String name;
}
