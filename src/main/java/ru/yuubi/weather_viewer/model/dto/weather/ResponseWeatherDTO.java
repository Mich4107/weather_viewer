package ru.yuubi.weather_viewer.model.dto.weather;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseWeatherDTO {
    private int locationId;
    private double longitude;
    private double latitude;
    private String description;
    private int temp;
    private int tempFeelsLike;
    private int pressure;
    private int humidity;
    private double windSpeed;
    private String locationName;
    private String countryCode;
    private String iconId;

    public ResponseWeatherDTO(double longitude, double latitude, String description,
                              int temp, int tempFeelsLike, int pressure, int humidity,
                              double windSpeed, String locationName, String countryCode,
                              String iconId) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.locationName = locationName;
        this.countryCode = countryCode;
        this.iconId = iconId;
    }
}

