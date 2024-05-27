package ru.yuubi.weather_viewer.service;

import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.LocationDAO;
import ru.yuubi.weather_viewer.model.dto.LocationDTO;
import ru.yuubi.weather_viewer.model.dto.forecast.RequestForecastDTO;
import ru.yuubi.weather_viewer.model.dto.forecast.ResponseForecastDTO;
import ru.yuubi.weather_viewer.model.dto.weather.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.model.dto.weather.RequestWeatherDTO;
import ru.yuubi.weather_viewer.model.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class WeatherService {
    private static final int ITEMS_PER_PAGE = 3;
    private LocationDAO locationDAO;

    public void saveLocation(Location location){
        locationDAO.save(location);
    }

    public List<Location> getLocationsByUserId (int userId) {
        return locationDAO.findByUserId(userId);
    }

    public void deleteLocation(int locationId) {
        locationDAO.delete(locationId);
    }


    public List<RequestForecastDTO> getDailyForecast(List<ResponseForecastDTO> hourlyForecast){
        int DAYS_LIMIT = 5;
        List<ResponseForecastDTO> dailyForecast = new ArrayList<>();

        String time = hourlyForecast.getFirst().getTime().replaceAll(" ", "T");
        LocalDateTime formattedTime = LocalDateTime.parse(time);
        int startDay = formattedTime.getDayOfMonth();

        int counter = 0;
        int daysCounter = 0;
        double temp = 0;

        List<String> iconsId = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        for(ResponseForecastDTO forecast : hourlyForecast) {

            String forecastTime = forecast.getTime().replaceAll(" ", "T");
            LocalDateTime formattedForecastTime = LocalDateTime.parse(forecastTime);


            if(daysCounter <= DAYS_LIMIT) {
                if(startDay == formattedForecastTime.getDayOfMonth()) {
                    temp += forecast.getTemp();
                    iconsId.add(forecast.getIconId());
                    descriptions.add(forecast.getDescription());
                    counter++;
                } else {

                    HashMap<String, Integer> iconsAppearance = new HashMap<>();
                    for(String icon : iconsId) {
                        iconsAppearance.put(icon, iconsAppearance.getOrDefault(icon, 0) + 1);
                    }

                    String icon = null;
                    int iconCount = 0;

                    for(Map.Entry<String, Integer> entry : iconsAppearance.entrySet()) {
                        if(icon == null) {
                            icon = entry.getKey();
                            iconCount = entry.getValue();
                        } else {
                            if(entry.getValue() > iconCount) {
                                icon = entry.getKey();
                                iconCount = entry.getValue();
                            }
                        }
                    }

                    HashMap<String, Integer> descriptionsAppearance = new HashMap<>();
                    for(String description : descriptions) {
                        descriptionsAppearance.put(description, descriptionsAppearance.getOrDefault(description, 0) + 1);
                    }

                    String description = null;
                    int descCount = 0;

                    for(Map.Entry<String, Integer> entry : descriptionsAppearance.entrySet()) {
                        if(description == null) {
                            description = entry.getKey();
                            descCount = entry.getValue();
                        } else {
                            if(entry.getValue() > descCount) {
                                description = entry.getKey();
                                descCount = entry.getValue();
                            }
                        }
                    }

                    double averageTemp = temp / counter;
                    ResponseForecastDTO responseForecast = new ResponseForecastDTO(averageTemp,
                            description, icon, formattedForecastTime.minusDays(1).toString(),
                            forecast.getName());
                    dailyForecast.add(responseForecast);

                    temp = 0;
                    counter = 0;
                    iconsId.clear();
                    descriptions.clear();
                    startDay++;
                    temp += forecast.getTemp();
                    counter++;
                    daysCounter++;
                }
            }
        }

        return convertToRequestForecastDtoList(dailyForecast);
    }


    public RequestWeatherDTO convertToRequestWeatherDto(ResponseWeatherDTO responseWeatherDTO) {
        int locationId = responseWeatherDTO.getLocationId();

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

        return new RequestWeatherDTO(locationId, lat, lon, locationInfo,
                weatherDescription, temp, tempFeelsLike, pressure, humidity, windSpeed, iconUrl);
    }

    public List<RequestForecastDTO> convertToRequestForecastDtoList(List<ResponseForecastDTO> forecasts){
        List<RequestForecastDTO> formattedForecasts = new ArrayList<>();
        for(ResponseForecastDTO forecast : forecasts) {
            int roundedTemp = (int) Math.round(forecast.getTemp());
            String formattedTemp;
            if(roundedTemp > 0) {
                formattedTemp = "+"+roundedTemp+"°";
            }  else {
                formattedTemp = roundedTemp+"°";
            }
            String description = forecast.getDescription();
            String iconUrl = "https://openweathermap.org/img/wn/"+forecast.getIconId()+"@2x.png";
            String time = forecast.getTime().replaceAll(" ", "T");
            LocalDateTime formattedTime = LocalDateTime.parse(time);
            String name = forecast.getName();

            RequestForecastDTO formattedForecast = new RequestForecastDTO(formattedTemp, description, iconUrl, formattedTime, name);
            formattedForecasts.add(formattedForecast);
        }
        return formattedForecasts;
    }

    public List<RequestWeatherDTO> formatDescriptions(List<ResponseWeatherDTO> descriptions) {
        List<RequestWeatherDTO> formattedDescriptions = new ArrayList<>();

        for(ResponseWeatherDTO description : descriptions) {
            RequestWeatherDTO formattedDescription = convertToRequestWeatherDto(description);
            formattedDescriptions.add(formattedDescription);
        }

        return formattedDescriptions;
    }

    public boolean isUserAlreadyHasThisLocation(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        int userId = location.getUserId();

        Location userLocation = locationDAO.findByLatAndLonAndUserId(lat, lon, userId);

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

    public WeatherService() {
        this.locationDAO = new LocationDAO();
    }

    public WeatherService(SessionFactory sessionFactory) {
        this.locationDAO = new LocationDAO(sessionFactory);
    }

    public List<LocationDTO> roundCoordinates(List<LocationDTO> locations) {
        for(LocationDTO location : locations) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            BigDecimal bigDecimalLat = new BigDecimal(Double.toString(lat));
            BigDecimal bigDecimalLon = new BigDecimal(Double.toString(lon));

            bigDecimalLat = bigDecimalLat.setScale(4, RoundingMode.HALF_UP);
            bigDecimalLon = bigDecimalLon.setScale(4, RoundingMode.HALF_UP);

            double roundedLat = bigDecimalLat.doubleValue();
            double roundedLon = bigDecimalLon.doubleValue();

            location.setLatitude(roundedLat);
            location.setLongitude(roundedLon);
        }
        return locations;
    }
}
