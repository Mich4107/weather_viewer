package ru.yuubi.weather_viewer.service;

import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.LocationDAO;
import ru.yuubi.weather_viewer.dto.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.dto.WeatherDescriptionDTO;
import ru.yuubi.weather_viewer.entity.Location;

import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private static final int ITEMS_PER_PAGE = 3;
    private LocationDAO locationDAO;

    public void saveLocation(Location location){
        locationDAO.saveLocation(location);
    }

    public List<Location> getLocationsByUserId (int userId) {
        return locationDAO.getLocationsByUserId(userId);
    }

    public void deleteLocation(int locationId) {
        locationDAO.deleteLocation(locationId);
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
                tempFeelsLike, pressure, humidity, windSpeed, iconUrl, lat, lon);
    }

    public boolean isUserAlreadyHasThisLocation(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        int userId = location.getUserId();

        Location userLocation = locationDAO.getLocationByLatAndLon(lat, lon, userId);

        return userLocation != null;
    }

    public int getPageNumber(String pageNumberFromReq) {
        if(pageNumberFromReq == null) {
            return  1;
        } else {
            return Integer.parseInt(pageNumberFromReq);
        }
    }

    public int getTotalPages(int listSize) {
        return (int) Math.ceil((double) listSize / ITEMS_PER_PAGE);
    }

    public int getStartIndex(int pageNumber) {
        return (ITEMS_PER_PAGE*pageNumber)-ITEMS_PER_PAGE;
    }

    public int getEndIndex(int pageNumber, int listSize) {
        return Math.min(ITEMS_PER_PAGE*pageNumber, listSize);
    }



    public List<WeatherDescriptionDTO> getDescriptions(List<Location> locations, WeatherApiService weatherApiService) {
        List<WeatherDescriptionDTO> descriptionsOfUserLocations = new ArrayList<>();
        for(Location location : locations) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            ResponseWeatherDTO responseWeatherDTO = weatherApiService.getWeatherByCoordinates(lat, lon);

            WeatherDescriptionDTO weatherDescription = createDescriptionFromResponseDto(responseWeatherDTO);
            weatherDescription.setLocationId(location.getId());

            descriptionsOfUserLocations.add(weatherDescription);
        }
        return descriptionsOfUserLocations;
    }

    public WeatherService() {
        this.locationDAO = new LocationDAO();
    }

    public WeatherService(SessionFactory sessionFactory) {
        this.locationDAO = new LocationDAO(sessionFactory);
    }
}
