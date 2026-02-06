package com.example.demo.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger logger = Logger.getLogger(SessionListener.class.getName());
    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = activeSessions.incrementAndGet();
        logger.info("Session created: " + se.getSession().getId() + " | Active sessions: " + count);
        se.getSession().getServletContext().setAttribute("activeSessions", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = activeSessions.decrementAndGet();
        logger.info("Session destroyed: " + se.getSession().getId() + " | Active sessions: " + count);
        se.getSession().getServletContext().setAttribute("activeSessions", count);
    }
}
