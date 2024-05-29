import config.HibernateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yuubi.weather_viewer.model.User;
import ru.yuubi.weather_viewer.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationServiceTest {

    private AuthenticationService authenticationService = new AuthenticationService(HibernateUtil.getSessionFactory());

    @Test
    void shouldGetUserByLogin() {
        User user1 = new User("123", "456");
        authenticationService.saveUser(user1);

        User user2 = authenticationService.getUserByLogin("123");

        String user1Login = user1.getLogin();
        String user2Login = user2.getLogin();

        assertEquals(user1Login, user2Login);
    }

    @Test
    void shouldReturnNullWhenUserDoesntExists(){
        Assertions.assertNull(authenticationService.getUserByLoginAndPassword("123", "123"));
    }
}