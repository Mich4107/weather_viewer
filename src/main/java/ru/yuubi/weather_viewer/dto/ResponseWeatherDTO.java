package ru.yuubi.weather_viewer.dto;


public class ResponseWeatherDTO {
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

    public ResponseWeatherDTO() {
    }

    public ResponseWeatherDTO(double longitude, double latitude, String description,
                              int temp, int tempFeelsLike, int pressure, int humidity,
                              double windSpeed, String locationName, String countryCode, String iconId) {
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

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                ", temp=" + temp +
                ", tempFeelsLike=" + tempFeelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", locationName='" + locationName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
