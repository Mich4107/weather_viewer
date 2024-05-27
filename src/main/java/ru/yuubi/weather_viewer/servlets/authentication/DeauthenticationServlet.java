package ru.yuubi.weather_viewer.servlets.authentication;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.servlets.BaseServlet;

import java.io.IOException;

@WebServlet("/logout")
public class DeauthenticationServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";

        if(cookies == null) {
            resp.sendRedirect("/signin");
            return;
        }

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(searchCookieName)) {
                String sessionGUIDValue = cookie.getValue();
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                authenticationService.removeSessionEntity(sessionGUIDValue);
            }
        }
        resp.sendRedirect("/signin");
    }

}
