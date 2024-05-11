package ru.yuubi.weather_viewer.service;

import org.json.JSONObject;
import org.json.JSONTokener;
import ru.yuubi.weather_viewer.dto.WeatherDTO;
import ru.yuubi.weather_viewer.utils.ConfigReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPIService {
    private static final String apiKey = ConfigReaderUtil.getApiKey();
    private static final String url = "https://api.openweathermap.org/data/2.5/weather";
    public WeatherDTO getWeatherByCityName(String name) {
        String response;
        try {
            response = sendRequestToApi(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONTokener jsonTokener = new JSONTokener(response);
        JSONObject jsonObject = new JSONObject(jsonTokener);

        return getWeatherDtoFromJson(jsonObject);
    }

    public WeatherDTO getWeatherByCoordinates(double lat, double lon){
        String response;
        try {
            response = sendRequestToApi(lat, lon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONTokener jsonTokener = new JSONTokener(response);
        JSONObject jsonObject = new JSONObject(jsonTokener);

        return getWeatherDtoFromJson(jsonObject);
    }

    private String sendRequestToApi(String cityName) throws IOException {
        String requestUrl = url+"?q="+cityName+"&appid="+apiKey;
        return getResponse(requestUrl);
    }

    private String sendRequestToApi(double latitude, double longitude) throws IOException {
        String requestUrl = url+"?lat="+latitude+"&lon="+longitude+"&appid="+apiKey;
        return getResponse(requestUrl);
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
            throw new RuntimeException("Can't reach request to the API");
        }
    }

    private WeatherDTO getWeatherDtoFromJson(JSONObject jsonObject){
        double latitude = jsonObject.getJSONObject("coord").getDouble("lat");
        double longitude = jsonObject.getJSONObject("coord").getDouble("lon");
        String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        double temperatureFeelsLike = jsonObject.getJSONObject("main").getDouble("feels_like");
        String locationName = jsonObject.getString("name");

        int tempInCelsius = (int) Math.round(temperature - 273.15);
        int tempFeelsLikeInCelsius = (int) Math.round(temperatureFeelsLike - 273.15);

        return new WeatherDTO(longitude, latitude, description, tempInCelsius, tempFeelsLikeInCelsius, locationName);
    }
}
