package ru.yuubi.weather_viewer.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;

import java.io.IOException;

@WebFilter({"/signup", "/signin"})
public class AuthFilter implements Filter {
    private SessionDAO sessionDAO = new SessionDAO();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";

        if(cookies == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        for(Cookie c : cookies) {
            if(c.getName().equals(searchCookieName)) {
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);
                if(sessionEntity != null) {
                    resp.sendRedirect("/home");
                    return;
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
