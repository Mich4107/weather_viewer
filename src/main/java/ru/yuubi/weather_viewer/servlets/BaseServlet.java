package ru.yuubi.weather_viewer.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.yuubi.weather_viewer.service.AuthService;
import ru.yuubi.weather_viewer.service.WeatherApiService;
import ru.yuubi.weather_viewer.service.WeatherService;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected AuthService authService = new AuthService();
    protected WeatherService weatherService = new WeatherService();
    protected WeatherApiService weatherApiService = new WeatherApiService();
    protected TemplateEngine templateEngine;
    protected IWebExchange webExchange;
    protected WebContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        context = new WebContext(webExchange);

        super.service(req, resp);
    }
}
