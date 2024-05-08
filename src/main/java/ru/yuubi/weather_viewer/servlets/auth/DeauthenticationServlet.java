package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.dao.SessionDAO;

import java.io.IOException;

@WebServlet("/logout")
public class DeauthenticationServlet extends HttpServlet {

    private SessionDAO sessionDAO = new SessionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionGUID";
        String sessionGUIDValue;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(searchCookieName)) {
                sessionGUIDValue = cookie.getValue();
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                sessionDAO.removeSession(sessionGUIDValue);
            }
        }
        resp.sendRedirect("/signin");
    }

}
