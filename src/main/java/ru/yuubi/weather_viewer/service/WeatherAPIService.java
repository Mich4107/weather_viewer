package ru.yuubi.weather_viewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.yuubi.weather_viewer.dto.WeatherDTO;
import ru.yuubi.weather_viewer.dto.LocationDTO;
import ru.yuubi.weather_viewer.exception.WeatherAPIException;
import ru.yuubi.weather_viewer.utils.ConfigReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherAPIService {
    private static final String API_KEY = ConfigReaderUtil.getApiKey();
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String LOCATIONS_URL = "http://api.openweathermap.org/geo/1.0/direct";

    public WeatherDTO getWeatherByCoordinates(double lat, double lon){
        String requestUrl = WEATHER_URL +"?lat="+lat+"&lon="+lon+"&appid="+ API_KEY +"&units=metric";
        try {
            String jsonResponse = getResponse(requestUrl);
            return getWeatherFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationDTO> getLocationsByCityName(String name) {
        String requestURL = LOCATIONS_URL+"?q="+name+"&limit="+10+"&appid="+API_KEY;
        try {
            String jsonResponse = getResponse(requestURL);
            return getLocationsFromJson(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<LocationDTO> getLocationsFromJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        List<LocationDTO> locations = new ArrayList<>();
        for (int i = 0; i < rootNode.size(); i++) {
            String name = rootNode.get(i).get("name").asText();
            double lat = rootNode.get(i).get("lat").asDouble();
            double lon = rootNode.get(i).get("lon").asDouble();
            String countryCode = rootNode.get(i).get("country").asText();
            String state = rootNode.get(i).get("state").asText();

            LocationDTO locationDTO = new LocationDTO(name, lat, lon, countryCode, state);
            locations.add(locationDTO);
        }
        return locations;
    }

    public WeatherDTO getWeatherFromJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        double latitude = rootNode.get("coord").get("lat").asDouble();
        double longitude = rootNode.get("coord").get("lon").asDouble();
        String description = rootNode.get("weather").get(0).get("description").asText();
        double temp = rootNode.get("main").get("temp").asDouble();
        double tempFeelsLike = rootNode.get("main").get("feels_like").asDouble();
        String locationName = rootNode.get("name").asText();
        String countryCode = rootNode.get("sys").get("country").asText();
        String iconId = rootNode.get("weather").get(0).get("icon").asText();

        int roundedTemp = (int) Math.round(temp);
        int roundedTempFeelsLike = (int) Math.round(tempFeelsLike);
        return new WeatherDTO(longitude, latitude, description, roundedTemp, roundedTempFeelsLike, locationName, countryCode, iconId);
    }

    private String getResponse(String requestUrl) throws IOException {
        URL obj = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            return response.toString();
        } else {
            throw new WeatherAPIException("Something went wrong in the request to the API");
        }
    }
}
