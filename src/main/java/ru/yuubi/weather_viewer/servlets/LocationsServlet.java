package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.model.dto.LocationDTO;
import ru.yuubi.weather_viewer.model.dto.weather.ResponseWeatherDTO;
import ru.yuubi.weather_viewer.model.Location;
import ru.yuubi.weather_viewer.model.Session;
import ru.yuubi.weather_viewer.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/locations")
public class LocationsServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cityName = openWeatherApiService.fillSpaces(req.getParameter("city_name"));
        String status = req.getParameter("status");

        if (status != null) {
            context.setVariable("userAlreadyHasThisLocation", true);
        }

        HttpSession httpSession = req.getSession();
        Session session = (Session) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");

        int userId = session.getUserId();
        String login = authenticationService.getUserLoginById(userId);

        List<LocationDTO> locations = openWeatherApiService.getLocationsByCityName(cityName);
        locations = weatherService.roundCoordinates(locations);

        context.setVariable("city_name", cityName);
        context.setVariable("locations", locations);
        context.setVariable("userLogin", login);
        templateEngine.process("locations", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double lat = Double.parseDouble(req.getParameter("lat"));
        double lon = Double.parseDouble(req.getParameter("lon"));
        String userLogin = req.getParameter("user");

//        ResponseWeatherDTO responseWeatherDTO = openWeatherApiService.getWeatherByCoordinates(lat, lon);
        User user = authenticationService.getUserByLogin(userLogin);
        String locationName = req.getParameter("location_name");
        int userId = user.getId();

        Location location = new Location(locationName, userId, lat, lon);

        if (weatherService.isUserAlreadyHasThisLocation(location)) {
            String cityName = req.getParameter("city_name");
            String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
            resp.sendRedirect("/locations?city_name=" + encodedCityName + "&status=hasLoc");
            return;
        }

        weatherService.saveLocation(location);

        resp.sendRedirect("/home");
    }
}
