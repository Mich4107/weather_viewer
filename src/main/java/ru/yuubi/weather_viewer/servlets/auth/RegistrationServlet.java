package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.dao.UserDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.service.AuthService;
import ru.yuubi.weather_viewer.utils.HibernateUtil;
import ru.yuubi.weather_viewer.utils.PasswordEncryptorUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    private AuthService authService = new AuthService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        if(authService.isSessionCookieExists(req)) {
//            resp.sendRedirect("/home");
//            return;
//        }
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        User user = new User(login, password);
        if(authService.getUserByLogin(login) != null) {
            context.setVariable("userAlreadyExists", "login already exists");
            templateEngine.process("signup", context, resp.getWriter());
            return;
        }

        authService.saveUser(user);

        int userId = user.getId();

        Cookie cookie = new Cookie("sessionId", UUID.randomUUID().toString());
        cookie.setMaxAge(60*10);
        resp.addCookie(cookie);

        SessionEntity sessionEntity = new SessionEntity(cookie.getValue(), userId, LocalDateTime.now().plusSeconds(60*10));

        authService.saveSessionEntity(sessionEntity);

        resp.sendRedirect("/home");
    }
}
