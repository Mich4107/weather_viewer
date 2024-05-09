package ru.yuubi.weather_viewer.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.dao.UserDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.entity.User;

public class AuthService {
    private SessionDAO sessionDAO = new SessionDAO();
    private UserDAO userDAO = new UserDAO();

    public SessionEntity getSessionEntityByGUID(String GUID) {
        return sessionDAO.getSessionEntity(GUID);
    }

    public void saveSessionEntity(SessionEntity sessionEntity) {
        sessionDAO.save(sessionEntity);
    }

    public String getUserLoginFromSessionEntity(SessionEntity sessionEntity) {
        int userId = sessionEntity.getUserId();
        User user = userDAO.getUserById(userId);
        return user.getLogin();
    }

    public String getSessionCookieValue(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";

        for(Cookie c : cookies) {
            if(c.getName().equals(searchCookieName)) {
                String sessionGUID = c.getValue();
                return sessionGUID;
            }
        }
        return null;
    }

    public boolean isSessionCookieExists(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";

        for(Cookie c : cookies) {
            if(c.getName().equals(searchCookieName)) {
                String sessionGUID = c.getValue();
                SessionEntity sessionEntity = sessionDAO.getSessionEntity(sessionGUID);
                if(sessionEntity != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteSessionCookie(HttpServletRequest req, HttpServletResponse resp){
        Cookie[] cookies = req.getCookies();
        String searchCookieName = "sessionId";
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(searchCookieName)) {
                String sessionGUIDValue = cookie.getValue();
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                sessionDAO.removeSession(sessionGUIDValue);
            }
        }
    }
}
