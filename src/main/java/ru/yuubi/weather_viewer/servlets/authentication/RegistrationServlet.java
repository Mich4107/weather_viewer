package ru.yuubi.weather_viewer.servlets.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.lang3.StringEscapeUtils;
import ru.yuubi.weather_viewer.model.Session;
import ru.yuubi.weather_viewer.model.User;
import ru.yuubi.weather_viewer.servlets.BaseServlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/signup")
public class RegistrationServlet extends BaseServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String decodedLogin = StringEscapeUtils.unescapeHtml4(login);
        String decodedPassword = StringEscapeUtils.unescapeHtml4(password);

        User user = new User(decodedLogin, decodedPassword);
        if(authenticationService.getUserByLogin(decodedLogin) != null) {
            context.setVariable("userAlreadyExists", "login already exists");
            templateEngine.process("signup", context, resp.getWriter());
            return;
        }

        authenticationService.saveUser(user);

        int userId = user.getId();

        Cookie cookie = new Cookie("sessionId", UUID.randomUUID().toString());
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        Session session = new Session(cookie.getValue(), userId, LocalDateTime.now().plusSeconds(60*60*24));
        authenticationService.saveSessionEntity(session);

        resp.sendRedirect("/home");
    }
}
