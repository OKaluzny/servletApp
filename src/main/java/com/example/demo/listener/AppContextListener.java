package com.example.demo.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LocalDateTime startTime = LocalDateTime.now();
        sce.getServletContext().setAttribute("appStartTime", startTime);
        logger.info("Application started at " + startTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application stopped");
    }
}
