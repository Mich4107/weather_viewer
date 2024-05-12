package ru.yuubi.weather_viewer.dto;


public class WeatherDTO {

    private double longitude;

    private double latitude;

    private String description;

    private int temp;

    private int tempFeelsLike;

    private String locationName;

    private String countryCode;

    private String iconId;


    public WeatherDTO() {
    }

    public WeatherDTO(double longitude, double latitude, String description, int temp, int tempFeelsLike, String locationName, String countryCode, String iconId) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.locationName = locationName;
        this.countryCode = countryCode;
        this.iconId = iconId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(int tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    @Override
    public String toString() {
        return "WeatherDTO{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                ", temp=" + temp +
                ", tempFeelsLike=" + tempFeelsLike +
                ", locationName='" + locationName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
