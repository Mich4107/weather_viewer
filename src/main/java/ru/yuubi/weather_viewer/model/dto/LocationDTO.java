package ru.yuubi.weather_viewer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class LocationDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
}
