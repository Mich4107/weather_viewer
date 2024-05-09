package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.service.AuthService;

import java.io.IOException;

@WebServlet("/logout")
public class DeauthenticationServlet extends HttpServlet {

    private SessionDAO sessionDAO = new SessionDAO();
    private AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authService.deleteSessionCookie(req, resp);
        resp.sendRedirect("/signin");
    }

}
