package ru.yuubi.weather_viewer.service;

import org.hibernate.SessionFactory;
import ru.yuubi.weather_viewer.dao.SessionDAO;
import ru.yuubi.weather_viewer.dao.UserDAO;
import ru.yuubi.weather_viewer.model.SessionEntity;
import ru.yuubi.weather_viewer.model.User;
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
        User user = userDAO.findByLogin(login);
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
        return userDAO.findByLogin(login);
    }

    public String getUserLoginById(int userId) {
        return userDAO.findById(userId).getLogin();
    }

    public void saveSessionEntity(SessionEntity sessionEntity) {
        sessionDAO.save(sessionEntity);
    }
    public void removeSessionEntity(String GUID) {
        sessionDAO.remove(GUID);
    }

    public AuthService() {
        this.userDAO = new UserDAO();
        this.sessionDAO = new SessionDAO();
    }

    public AuthService(SessionFactory sessionFactory){
        this.userDAO = new UserDAO(sessionFactory);
        this.sessionDAO = new SessionDAO(sessionFactory);
    }
}
