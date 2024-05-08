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
import ru.yuubi.weather_viewer.model.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private SessionDAO sessionDAO = new SessionDAO();
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "JSESSIONID";

        for(Cookie c : cookies) {
            System.out.println("JAJAJAJAJAJA");
            if(c.getName().equals(searchCookieName)) {
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);
                if(sessionEntity != null) {
                    System.out.println("Test sessionEntity != null~~~~");
                    resp.sendRedirect("/home");
                    return;
                }
                System.out.println("~~~~~~~YOU ARE HERE so sessionEntity == null~~~~~~~~~~~~~~~~~");

            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

        Cookie[] cookies = req.getCookies();
        String cookieName = "JSESSIONID";
        Cookie cookie = null;

        for(Cookie c : cookies) {
            if(c.getName().equals(cookieName)) {
                cookie = c;
                break;
            }
        }
        if(cookie != null) {
            cookie.setMaxAge(60*60*24);
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        } else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~JSESSIONID DIDN'T CREATED~~~~~~~~~~~~REGISTR~~~~~~~~~~~~~~~~~~~~~~~~~");
            cookie = new Cookie("JSESSIONID", req.getSession().getId());
            cookie.setMaxAge(60*60*24);
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
        }
        String sessionGUID = cookie.getValue();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tomorrow.format(formatter);

        SessionEntity sessionEntity = new SessionEntity(sessionGUID, userId, formattedDateTime);

        sessionDAO.save(sessionEntity);

        resp.sendRedirect("/home");
    }
}
