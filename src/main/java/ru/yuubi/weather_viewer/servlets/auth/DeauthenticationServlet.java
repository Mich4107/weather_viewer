package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.servlets.BaseServlet;

import java.io.IOException;

@WebServlet("/logout")
public class DeauthenticationServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/signin");
    }

}
