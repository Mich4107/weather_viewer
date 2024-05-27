package ru.yuubi.weather_viewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.yuubi.weather_viewer.model.dto.forecast.ResponseForecastDTO;
import ru.yuubi.weather_viewer.model.dto.weather.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.model.dto.LocationDTO;
import ru.yuubi.weather_viewer.model.Location;
import ru.yuubi.weather_viewer.exception.WeatherApiCallException;
import ru.yuubi.weather_viewer.utils.ConfigReaderUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherApiService {
    private static final String API_KEY = ConfigReaderUtil.getApiKey();
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String LOCATIONS_URL = "https://api.openweathermap.org/geo/1.0/direct";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private HttpClient client;

    public ResponseWeatherDTO getWeatherByCoordinates(double lat, double lon){
        String requestUrl = WEATHER_URL
                +"?lat="+lat
                +"&lon="+lon
                +"&appid="+API_KEY
                +"&units=metric";
        try {
            String jsonResponse = getResponse(requestUrl);
            return getWeatherFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ResponseWeatherDTO> getWeathersByLocations(List<Location> locations) {
        List<ResponseWeatherDTO> descriptionsOfWeathers = new ArrayList<>();

        for(Location location : locations) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            ResponseWeatherDTO description = getWeatherByCoordinates(latitude, longitude);
            description.setLocationId(location.getId());

            descriptionsOfWeathers.add(description);
        }

        return descriptionsOfWeathers;
    }

    public List<ResponseForecastDTO> getHourlyForecastByCoordinates(double lat, double lon){
        String requestUrl = FORECAST_URL
                +"?lat="+lat
                +"&lon="+lon
                +"&cnt="+5
                +"&units=metric"
                +"&appid="+API_KEY;
        try{
            String jsonResponse = getResponse(requestUrl);
            return getForecastFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ResponseForecastDTO> getHourlyForecastForFiveDaysByCoordinates(double lat, double lon){
        String requestUrl = FORECAST_URL
                +"?lat="+lat
                +"&lon="+lon
                +"&cnt="+40
                +"&units=metric"
                +"&appid="+API_KEY;
        try{
            String jsonResponse = getResponse(requestUrl);
            return getForecastFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationDTO> getLocationsByCityName(String name) {
        String requestURL = LOCATIONS_URL
                +"?q="+name
                +"&limit="+5
                +"&appid="+API_KEY;
        try {
            String jsonResponse = getResponse(requestURL);
            return getLocationsFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResponse(String requestUrl) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(response.statusCode() == 200) {
            return response.body();
        } else {
            throw new WeatherApiCallException("Something went wrong in the request to the API");
        }
    }
    private ResponseWeatherDTO getWeatherFromJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        double latitude = rootNode.get("coord").get("lat").asDouble();
        double longitude = rootNode.get("coord").get("lon").asDouble();
        String description = rootNode.get("weather").get(0).get("description").asText();
        double temp = rootNode.get("main").get("temp").asDouble();
        double tempFeelsLike = rootNode.get("main").get("feels_like").asDouble();
        int pressure = rootNode.get("main").get("pressure").asInt();
        int humidity = rootNode.get("main").get("humidity").asInt();
        double windSpeed = rootNode.get("wind").get("speed").asDouble();
        String locationName = rootNode.get("name").asText();
        String countryCode = rootNode.get("sys").get("country").asText();
        String iconId = rootNode.get("weather").get(0).get("icon").asText();

        pressure = convertToMmHg(pressure);
        int roundedTemp = (int) Math.round(temp);
        int roundedTempFeelsLike = (int) Math.round(tempFeelsLike);

        return new ResponseWeatherDTO(longitude, latitude, description,
                roundedTemp, roundedTempFeelsLike, pressure, humidity,
                windSpeed, locationName, countryCode, iconId);
    }

    private List<LocationDTO> getLocationsFromJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        List<LocationDTO> locations = new ArrayList<>();
        for (int i = 0; i < rootNode.size(); i++) {
            String name = rootNode.get(i).get("name").asText();
            double lat = rootNode.get(i).get("lat").asDouble();
            double lon = rootNode.get(i).get("lon").asDouble();
            String countryCode = rootNode.get(i).get("country").asText();

            String state;

            if(rootNode.get(i).get("state") != null) {
                state = rootNode.get(i).get("state").asText();
            } else {
                state = null;
            }

            LocationDTO locationDTO = new LocationDTO(name, lat, lon, countryCode, state);
            locations.add(locationDTO);
        }
        return locations;
    }

    private List<ResponseForecastDTO> getForecastFromJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        List<ResponseForecastDTO> forecast = new ArrayList<>();
        JsonNode list = rootNode.get("list");
        for (int i = 0; i < list.size(); i++) {
            double temp = list.get(i).get("main").get("temp").asDouble();
            String description = list.get(i).get("weather").get(0).get("description").asText();
            String iconId = list.get(i).get("weather").get(0).get("icon").asText();
            String time = list.get(i).get("dt_txt").asText();
            String name = rootNode.get("city").get("name").asText();
            ResponseForecastDTO responseForecastDTO = new ResponseForecastDTO(temp, description, iconId, time, name);
            forecast.add(responseForecastDTO);
        }
        return forecast;
    }

    public String fillSpaces(String cityName) {
        if(cityName.contains(" ")) {
            cityName = cityName.replaceAll(" ", "%20");
        }
        return cityName;
    }
    private int convertToMmHg(int pressure) {
        return (int) Math.round(pressure * 0.75006157);
    }
    public OpenWeatherApiService() {
        this.client = HttpClient.newHttpClient();
    }
    public OpenWeatherApiService(HttpClient client) {
        this.client = client;
    }
}
