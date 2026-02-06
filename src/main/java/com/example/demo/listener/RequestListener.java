package com.example.demo.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

@WebListener
public class RequestListener implements ServletRequestListener {

    private static final Logger logger = Logger.getLogger(RequestListener.class.getName());
    private static final String START_TIME_ATTR = "requestStartTime";

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletRequest().setAttribute(START_TIME_ATTR, System.currentTimeMillis());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        Long startTime = (Long) sre.getServletRequest().getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            String uri = "";
            if (sre.getServletRequest() instanceof HttpServletRequest httpReq) {
                uri = httpReq.getRequestURI();
            }
            logger.info("Request to " + uri + " took " + duration + " ms");
        }
    }
}
