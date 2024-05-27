package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.lang3.StringEscapeUtils;
import ru.yuubi.weather_viewer.model.SessionEntity;
import ru.yuubi.weather_viewer.model.User;
import ru.yuubi.weather_viewer.servlets.BaseServlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/signin")
public class AuthenticationServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("status") != null) {
            context.setVariable("userIsNotValid", "wrong login / password");
            templateEngine.process("signin", context, resp.getWriter());
            return;
        }

        getServletContext().getRequestDispatcher("/templates/signin.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String decodedLogin = StringEscapeUtils.unescapeHtml4(login);
        String decodedPassword = StringEscapeUtils.unescapeHtml4(password);

        User user = authService.getUserByLoginAndPassword(decodedLogin, decodedPassword);

        if(user == null) {
            resp.sendRedirect("/signin?status=wrong-password");
            return;
        }

        int userId = user.getId();

        Cookie cookie = new Cookie("sessionId", UUID.randomUUID().toString());
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        SessionEntity sessionEntity = new SessionEntity(cookie.getValue(), userId, LocalDateTime.now().plusSeconds(60*60*24));
        authService.saveSessionEntity(sessionEntity);

        resp.sendRedirect("/home");
    }
}
