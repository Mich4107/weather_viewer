package ru.yuubi.weather_viewer.dto;

public class WeatherDescriptionDTO {
    private int locationId;
    private double lat;
    private double lon;
    private String locationInfo;
    private String weatherDescription;
    private String temp;
    private String tempFeelsLike;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String iconUrl;

    public WeatherDescriptionDTO() {
    }

    public WeatherDescriptionDTO(String locationInfo, String weatherDescription, String temp,
                                 String tempFeelsLike, String pressure, String humidity,
                                 String windSpeed, String iconUrl, double lat, double lon) {
        this.locationInfo = locationInfo;
        this.weatherDescription = weatherDescription;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.iconUrl = iconUrl;
        this.lat = lat;
        this.lon = lon;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(String tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
