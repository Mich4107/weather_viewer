package ru.yuubi.weather_viewer.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.dao.UserDAO;
import ru.yuubi.weather_viewer.entity.SessionEntity;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.utils.PasswordEncryptorUtil;

public class AuthService {
    private SessionDAO sessionDAO;
    private UserDAO userDAO;

    public void saveUser(User user) {
        String password = user.getPassword();
        String hashedPassword = PasswordEncryptorUtil.hashPassword(password);
        user.setPassword(hashedPassword);
        userDAO.save(user);
    }

    public User getUserByLoginAndPassword(String login, String password) {
        User user = userDAO.getUserByLogin(login);
        if(user != null) {
            String hashedPasswordFromDB = user.getPassword();
            boolean isPasswordsEquals = PasswordEncryptorUtil.checkPassword(password, hashedPasswordFromDB);
            if(isPasswordsEquals) {
                return user;
            }
        }
        return null;
    }

    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }


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


    /**
     * If empty parameter -> creating DAO's with default session factory
     */
    public AuthService() {
        this.userDAO = new UserDAO();
        this.sessionDAO = new SessionDAO();
    }

    /**
     * If it takes parameter -> creating DAO's with custom session factory (made for tests)
     */
    public AuthService(SessionFactory sessionFactory){
        this.userDAO = new UserDAO(sessionFactory);
        this.sessionDAO = new SessionDAO(sessionFactory);
    }
}
