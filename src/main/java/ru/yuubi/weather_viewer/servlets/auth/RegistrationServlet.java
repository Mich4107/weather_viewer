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
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.listener.ThymeleafListener;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signup.html").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try(factory) {
            Session session = factory.getCurrentSession();
            User user = new User(login, password);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }

//        HttpSession session = req.getSession();
//        String sessionUID = session.getId();
//        session.setAttribute();
//
//        Cookie cookie = new Cookie("sessionUID", sessionUID);
//        cookie.setMaxAge(60*60*24);
//        resp.addCookie(cookie);


//        System.out.println(session.getId());

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("loggedInUser", login);
        templateEngine.process("home", context, resp.getWriter());

        System.out.println("ADASASASAS");

    }
}
