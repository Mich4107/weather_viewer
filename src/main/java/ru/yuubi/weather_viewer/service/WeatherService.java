package ru.yuubi.weather_viewer.service;

import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.LocationDAO;
import ru.yuubi.weather_viewer.dto.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.dto.WeatherDescriptionDTO;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.util.List;

public class WeatherService {
    private LocationDAO locationDAO;

    public void saveLocation(Location location){
        locationDAO.saveLocation(location);
    }

    public List<Location> getLocationsByUserId (int userId) {
        return locationDAO.getLocationsByUserId(userId);
    }

    public WeatherDescriptionDTO createDescriptionFromResponseDto(ResponseWeatherDTO responseWeatherDTO) {
        String locationInfo = responseWeatherDTO.getLocationName() + ", " + responseWeatherDTO.getCountryCode();
        String weatherDescription = responseWeatherDTO.getDescription();

        int responseTemp = responseWeatherDTO.getTemp();
        int responseTempFeelsLike = responseWeatherDTO.getTempFeelsLike();

        String temp;
        String tempFeelsLike;

        if(responseTemp > 0) {
            temp = "+"+responseTemp+"°";
        }  else {
            temp = responseTemp+"°";
        }

        if(responseTempFeelsLike > 0) {
            tempFeelsLike = "Feels like +"+responseTempFeelsLike+"°";
        }  else {
            tempFeelsLike = "Feels like "+responseTempFeelsLike+"°";
        }

        String pressure = responseWeatherDTO.getPressure()+" mmHg";
        String humidity = responseWeatherDTO.getHumidity()+"%";
        String windSpeed = responseWeatherDTO.getWindSpeed()+" m/s";
        String iconUrl = "https://openweathermap.org/img/wn/"+responseWeatherDTO.getIconId()+"@2x.png";
        double lat = responseWeatherDTO.getLatitude();
        double lon = responseWeatherDTO.getLongitude();

        return new WeatherDescriptionDTO(locationInfo, weatherDescription, temp,
                tempFeelsLike, pressure, humidity, windSpeed, iconUrl);
    }

    public WeatherService() {
        this.locationDAO = new LocationDAO();
    }

    public WeatherService(SessionFactory sessionFactory) {
        this.locationDAO = new LocationDAO(sessionFactory);
    }
}
