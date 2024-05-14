package ru.yuubi.weather_viewer.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.dao.UserDAO;
import ru.yuubi.weather_viewer.dto.LocationDTO;
import ru.yuubi.weather_viewer.dto.WeatherDTO;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.service.WeatherApiService;
import ru.yuubi.weather_viewer.servlets.BaseServlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/locations")
public class LocationsServlet extends BaseServlet {
    private WeatherApiService weatherApiService = new WeatherApiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cityName = req.getParameter("city_name");

        HttpSession httpSession = req.getSession();
        SessionEntity sessionEntity = (SessionEntity) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");
        String login = authService.getUserLoginFromSessionEntity(sessionEntity);

        List<LocationDTO> locations = weatherApiService.getLocationsByCityName(cityName);

        context.setVariable("locations", locations);
        context.setVariable("userLogin", login);
        templateEngine.process("locations", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double lat = Double.parseDouble(req.getParameter("lat"));
        double lon = Double.parseDouble(req.getParameter("lon"));
        String userLogin = req.getParameter("user");

        WeatherDTO weatherDTO = weatherApiService.getWeatherByCoordinates(lat, lon);
        User user = authService.getUserByLogin(userLogin);
        String locationName = weatherDTO.getLocationName();
        int userId = user.getId();

        Location location = new Location(locationName, userId, lat, lon);

        weatherService.saveLocation(location);

        resp.sendRedirect("/home");
    }
}
