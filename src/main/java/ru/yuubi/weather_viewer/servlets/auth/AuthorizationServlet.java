package ru.yuubi.weather_viewer.servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.yuubi.weather_viewer.entity.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/signin")
public class AuthorizationServlet extends HttpServlet {
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
        getServletContext().getRequestDispatcher("/WEB-INF/templates/signin.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try(factory) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

            String hql = "from User where login = :login and password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            query.setParameter("password", password);
            User user = (User) query.uniqueResult();

            session.getTransaction().commit();

            if(user == null) {
                resp.sendRedirect("/signin?status=wrong-password");
                return;
            }
            resp.sendRedirect("/home");
        }

    }
}
