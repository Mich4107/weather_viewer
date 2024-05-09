import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yuubi.weather_viewer.entity.User;
import ru.yuubi.weather_viewer.service.AuthService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyTestClass {

    private static DataSource dataSource;
    private AuthService authService;


    @BeforeAll
    static void setDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");
        dataSource = ds;

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE users (ID int primary key, Login varchar(64) not null, Password varchar(64))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    public void init() {
        authService = new AuthService();
    }


    @Test
    public void testCreateUser() {
        User user = new User("testUser", "testPassword");
        authService.saveUser(user);

    }
}
