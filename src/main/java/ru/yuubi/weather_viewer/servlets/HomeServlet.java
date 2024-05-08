package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private SessionDAO sessionDAO = new SessionDAO();
    private UserDAO userDAO = new UserDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        Cookie[] cookies = req.getCookies();
        String cookieName = "sessionGUID";

        for(Cookie c : cookies) {
            if(c.getName().equals(cookieName)) {

                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);

                int userId = sessionEntity.getUserId();
                User user = userDAO.getUserById(userId);
                String login = user.getLogin();

                if(sessionEntity != null) {
                    context.setVariable("userLogin", login);
                    templateEngine.process("home", context, resp.getWriter());
                    return;
                }
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/templates/home.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/home.html").forward(req, resp);
    }
}
