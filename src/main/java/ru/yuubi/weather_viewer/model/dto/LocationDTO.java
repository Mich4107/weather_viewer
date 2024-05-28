package ru.yuubi.weather_viewer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
}
