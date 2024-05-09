import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.yuubi.weather_viewer.servlets.HomeServlet;
import ru.yuubi.weather_viewer.servlets.auth.RegistrationServlet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyTestClass {
    private RegistrationServlet registrationServlet;
    private HomeServlet homeServlet;

    @BeforeEach
    public void init() {
        registrationServlet = new RegistrationServlet();
        homeServlet = new HomeServlet();
    }


    @Test
    public void test() {

    }
}
