package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.dto.WeatherDescriptionDTO;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.io.IOException;
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

        int pageNumber = weatherService.getPageNumber(req.getParameter("page"));
        int totalPages = weatherService.getTotalPages(locations.size());
        int startIndex = weatherService.getStartIndex(pageNumber);
        int endIndex = weatherService.getEndIndex(pageNumber, locations.size());

        List<WeatherDescriptionDTO> descriptionsOfUserLocations = weatherService.getDescriptions(locations, weatherApiService);

        List<WeatherDescriptionDTO> descriptionsForParticularPage = descriptionsOfUserLocations.subList(startIndex, endIndex);

        context.setVariable("totalPages", totalPages);
        context.setVariable("currentPage", pageNumber);
        context.setVariable("userLocations", descriptionsForParticularPage);
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
