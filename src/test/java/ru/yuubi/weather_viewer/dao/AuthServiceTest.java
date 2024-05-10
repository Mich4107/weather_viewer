package ru.yuubi.weather_viewer.dao;

import org.junit.jupiter.api.Test;
import ru.yuubi.weather_viewer.dao.config.HibernateUtil;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.service.AuthService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthServiceTest {

    private AuthService authService = new AuthService(HibernateUtil.getSessionFactory());

    @Test
    void itShouldGetUserByLogin() {
        User user1 = new User("123", "456");
        authService.saveUser(user1);

        User user2 = authService.getUserByLogin("123");

        String user1Login = user1.getLogin();
        String user2Login = user2.getLogin();

        assertThat(user1Login).isEqualTo(user2Login);
    }
}