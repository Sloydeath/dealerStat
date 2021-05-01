package com.leverx.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * This class initialized Web Application
 *
 * @author Andrew Panas
 */
public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class, JpaConfig.class, SecurityConfig.class, RedisConfig.class, MailConfig.class);
        applicationContext.setServletContext(servletContext);

        // Dispatcher Servlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "springDispatcher", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
