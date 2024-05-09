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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private SessionDAO sessionDAO = new SessionDAO();
    private AuthService authService = new AuthService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(authService.isSessionCookieExists(req)) {
            resp.sendRedirect("home");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        User user = new User(login, password);
        if(userDAO.getUserByLogin(login) != null) {
            context.setVariable("userAlreadyExists", "login already exists");
            templateEngine.process("signup", context, resp.getWriter());
            return;
        }

        userDAO.save(user);

        int userId = user.getId();

        Cookie cookie = new Cookie("sessionId", UUID.randomUUID().toString());
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        SessionEntity sessionEntity = new SessionEntity(cookie.getValue(), userId, LocalDateTime.now().plusDays(1));

        sessionDAO.save(sessionEntity);

        resp.sendRedirect("/home");
    }
}
