package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.dto.forecast.RequestForecastDTO;
import ru.yuubi.weather_viewer.dto.forecast.ResponseForecastDTO;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.io.IOException;
import java.util.List;

@WebServlet("/forecast")
public class ForecastServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        SessionEntity sessionEntity = (SessionEntity) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");

        double lat = Double.parseDouble(req.getParameter("lat"));
        double lon = Double.parseDouble(req.getParameter("lon"));

        int userId = sessionEntity.getUserId();
        String login = authService.getUserLoginById(userId);

        List<ResponseForecastDTO> hourlyForecasts = openWeatherApiService.getHourlyForecastByCoordinates(lat, lon);
        List<RequestForecastDTO> formattedHourlyForecasts = weatherService.convertToRequestForecastDto(hourlyForecasts);

        context.setVariable("userLogin", login);
        context.setVariable("hourlyForecast", formattedHourlyForecasts);

        templateEngine.process("forecast", context, resp.getWriter());
    }
}
