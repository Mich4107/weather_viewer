package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.service.AuthService;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        SessionEntity sessionEntity = (SessionEntity) httpSession.getAttribute("sessionEntity");
        httpSession.removeAttribute("sessionEntity");
        String login = authService.getUserLoginFromSessionEntity(sessionEntity);

        context.setVariable("userLogin", login);
        templateEngine.process("home", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/home.html").forward(req, resp);
    }
}
