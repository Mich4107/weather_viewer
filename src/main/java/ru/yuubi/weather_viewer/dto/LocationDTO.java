package ru.yuubi.weather_viewer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
}
