package ru.yuubi.weather_viewer.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/home")
public class HomeFilter implements Filter {
    private SessionDAO sessionDAO = new SessionDAO();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";

        for(Cookie c : cookies) {
            if(c.getName().equals(searchCookieName)) {
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);

                if(sessionEntity != null) {
                    c.setMaxAge(60*10);
                    resp.addCookie(c);

                    sessionEntity.setExpiresAt(LocalDateTime.now().plusSeconds(60*10));
                    sessionDAO.update(sessionEntity);

                    HttpSession httpSession = req.getSession();
                    httpSession.setAttribute("sessionEntity", sessionEntity);

                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        resp.sendRedirect("/signup");
    }
}
