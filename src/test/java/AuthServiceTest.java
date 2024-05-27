import config.HibernateUtil;
import org.junit.jupiter.api.Test;
import ru.yuubi.weather_viewer.model.User;
import ru.yuubi.weather_viewer.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthServiceTest {

    private AuthService authService = new AuthService(HibernateUtil.getSessionFactory());

    @Test
    void shouldGetUserByLogin() {
        User user1 = new User("123", "456");
        authService.saveUser(user1);

        User user2 = authService.getUserByLogin("123");

        String user1Login = user1.getLogin();
        String user2Login = user2.getLogin();

        assertEquals(user1Login, user2Login);
    }
}