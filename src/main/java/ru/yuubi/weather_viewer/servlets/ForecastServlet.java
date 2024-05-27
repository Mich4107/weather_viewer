package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.model.dto.forecast.RequestForecastDTO;
import ru.yuubi.weather_viewer.model.dto.forecast.ResponseForecastDTO;
import ru.yuubi.weather_viewer.model.Session;

import java.io.IOException;
import java.util.List;

@WebServlet("/forecast")
public class ForecastServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        Session session = (Session) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");

        double lat = Double.parseDouble(req.getParameter("lat"));
        double lon = Double.parseDouble(req.getParameter("lon"));

        int userId = session.getUserId();
        String login = authService.getUserLoginById(userId);

        List<ResponseForecastDTO> hourlyForecasts = openWeatherApiService.getHourlyForecastByCoordinates(lat, lon);
        List<RequestForecastDTO> formattedHourlyForecasts = weatherService.convertToRequestForecastDto(hourlyForecasts);

        context.setVariable("userLogin", login);
        context.setVariable("hourlyForecast", formattedHourlyForecasts);

        templateEngine.process("forecast", context, resp.getWriter());
    }
}
