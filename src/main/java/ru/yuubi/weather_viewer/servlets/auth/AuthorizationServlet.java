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
import ru.yuubi.weather_viewer.utils.HibernateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/signin")
public class AuthorizationServlet extends HttpServlet {
    private SessionDAO sessionDAO = new SessionDAO();
    private UserDAO userDAO = new UserDAO();

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
        Cookie[] cookies = req.getCookies();
        String cookieName = "JSESSIONID";

        for(Cookie c : cookies) {
            if(c.getName().equals(cookieName)) {
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);
                if(sessionEntity != null) {
                    resp.sendRedirect("/home");
                    return;
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~ In AuthorizationServlet sessionEntity is NULL ~~~~~~~~~~~~~~~~~~~~~~");
            }
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

        Cookie[] cookies = req.getCookies();
        String searchCookieName = "JSESSIONID";
        Cookie jsessionidCookie = null;

        for(Cookie c : cookies) {
            if(c.getName().equals(searchCookieName)) {
                jsessionidCookie = c;
                break;
            }
        }

        if(jsessionidCookie != null) {
            jsessionidCookie.setMaxAge(60*60*24);
            jsessionidCookie.setHttpOnly(true);
        } else {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~JSESSIONID IS NULL~~~~~~~~~~~~~~~AUTHO~~~~~~~~~~~~~~~~~~~~~~");
            jsessionidCookie = new Cookie("JSESSIONID", req.getSession().getId());
            jsessionidCookie.setMaxAge(60*60*24);
            jsessionidCookie.setHttpOnly(true);
            resp.addCookie(jsessionidCookie);
        }

        String sessionGUID = jsessionidCookie.getValue();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tomorrow.format(formatter);

        SessionEntity sessionEntity = new SessionEntity(sessionGUID, userId, formattedDateTime);

        sessionDAO.save(sessionEntity);

        resp.sendRedirect("/home");
    }
}
