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

@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private SessionDAO sessionDAO = new SessionDAO();
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String cookieName = "sessionGUID";

        for(Cookie c : cookies) {
            if(c.getName().equals(cookieName)) {
                System.out.println("In /signup servlet isHttpOnly: "+c.isHttpOnly()+", getSecure(): "+c.getSecure()+", getDomain: "+c.getDomain());
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);
                if(sessionEntity != null) {
                    resp.sendRedirect("/home");
                    return;
                }
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

        HttpSession browserSession = req.getSession();

        String sessionGUID = browserSession.getId();

        //browserSession.setMaxInactiveInterval(0);
        //если оставлять это только здесь а в authorization этого не писать,
        //то если signin - то он редиректит и на signin и на signup
        //а если signup - то только через signup редиректит
        //а вы authorization servlet такая фишка не работает
        browserSession.setMaxInactiveInterval(60*60*24);


        Cookie cookie = new Cookie("sessionGUID", sessionGUID);
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = tomorrow.format(formatter);

        SessionEntity sessionEntity = new SessionEntity(sessionGUID, userId, formattedDateTime);
        sessionDAO.save(sessionEntity);

        resp.sendRedirect("/home");
    }
}
