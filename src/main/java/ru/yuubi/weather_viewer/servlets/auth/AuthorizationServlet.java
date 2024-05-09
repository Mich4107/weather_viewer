package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@WebServlet("/signin")
public class AuthorizationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private AuthService authService = new AuthService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("status") != null) {
            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
            WebContext context = new WebContext(webExchange);
            context.setVariable("userIsNotValid", "wrong login / password");
            templateEngine.process("signin", context, resp.getWriter());
            return;
        }

        if(authService.isSessionCookieExists(req)) {
            resp.sendRedirect("/home");
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/templates/signin.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userDAO.getUserByLoginAndPassword(login, password);

        if(user == null) {
            resp.sendRedirect("/signin?status=wrong-password");
            return;
        }

        int userId = user.getId();

        Cookie cookie = new Cookie("sessionId", UUID.randomUUID().toString());
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        SessionEntity sessionEntity = new SessionEntity(cookie.getValue(), userId, LocalDateTime.now().plusDays(1));
        authService.saveSessionEntity(sessionEntity);

        resp.sendRedirect("/home");
    }
}
