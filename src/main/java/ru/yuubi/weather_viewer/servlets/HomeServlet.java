package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.dto.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.dto.WeatherDescriptionDTO;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        SessionEntity sessionEntity = (SessionEntity) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");
        String login = authService.getUserLoginFromSessionEntity(sessionEntity);

        int userId = sessionEntity.getUserId();
        List<Location> locations = weatherService.getLocationsByUserId(userId);


        List<WeatherDescriptionDTO> descriptionsOfUserLocations = new ArrayList<>();

        for(Location location : locations) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            ResponseWeatherDTO responseWeatherDTO = weatherApiService.getWeatherByCoordinates(lat, lon);

            WeatherDescriptionDTO weatherDescription = weatherService.createDescriptionFromResponseDto(responseWeatherDTO);
            weatherDescription.setLocationId(location.getId());

            descriptionsOfUserLocations.add(weatherDescription);
        }



        context.setVariable("userLocations", descriptionsOfUserLocations);
        context.setVariable("userLogin", login);
        templateEngine.process("home", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int locationId = Integer.parseInt(req.getParameter("locationId"));

        weatherService.deleteLocation(locationId);

        resp.sendRedirect("/home");
    }
}
