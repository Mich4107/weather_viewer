package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.yuubi.weather_viewer.entity.Location;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.listener.ThymeleafListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession browserSession = req.getSession();
        browserSession.setMaxInactiveInterval(60*60*24);

        String sessionGUID = browserSession.getId();

        Cookie cookie = new Cookie("sessionGUID", sessionGUID);
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        SessionFactory factory = (SessionFactory) getServletContext().getAttribute("sessionFactory");

        try(factory) {
            Session hibernateSession = factory.getCurrentSession();

            User user = new User(login, password);

            hibernateSession.beginTransaction();
            hibernateSession.persist(user);
            hibernateSession.getTransaction().commit();

            int userId = user.getId();

//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime tomorrow = now.plusDays(1);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = tomorrow.format(formatter);
//
//            ru.yuubi.weather_viewer.entity.Session session = new ru.yuubi.weather_viewer.entity.Session(sessionGUID, userId, formattedDateTime);


        }






//        System.out.println(session.getId());

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("loggedInUser", login);
        templateEngine.process("home", context, resp.getWriter());

        System.out.println("ADASASASAS");

    }
}
