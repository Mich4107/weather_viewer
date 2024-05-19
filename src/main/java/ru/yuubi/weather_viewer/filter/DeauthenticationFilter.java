package ru.yuubi.weather_viewer.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.dao.SessionDAO;

import java.io.IOException;

@WebFilter("/logout")
public class DeauthenticationFilter implements Filter {

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

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(searchCookieName)) {
                String sessionGUIDValue = cookie.getValue();
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                sessionDAO.removeSession(sessionGUIDValue);
            }
        }
        filterChain.doFilter(req,resp);
    }
}
