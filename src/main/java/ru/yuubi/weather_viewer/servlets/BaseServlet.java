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
import ru.yuubi.weather_viewer.service.AuthenticationService;
import ru.yuubi.weather_viewer.service.OpenWeatherApiService;
import ru.yuubi.weather_viewer.service.WeatherService;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected AuthenticationService authenticationService = new AuthenticationService();
    protected WeatherService weatherService = new WeatherService();
    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected TemplateEngine templateEngine;
    protected IWebExchange webExchange;
    protected WebContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        context = new WebContext(webExchange);
        try{
            super.service(req, resp);
        } catch (Exception e) {
            context.setVariable("error", e.getMessage());
            templateEngine.process("error", context, resp.getWriter());
        }
    }
}
