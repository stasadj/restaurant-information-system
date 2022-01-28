package com.restaurant.backend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FrontendRedirectFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(FrontendRedirectFilter.class);

    private final String REGEX = "(?!/h2-console|/actuator|/api|/_nuxt|/index\\.html|/200\\.html|/favicon\\.ico|/sw\\.js|/static|/build).*$";
    private final Pattern pattern = Pattern.compile(REGEX);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        if (pattern.matcher(req.getRequestURI()).matches() && !req.getRequestURI().equals("/")) {
            // Delegate/Forward to `/` if `pattern` matches and it is not `/`
            // Required because of 'mode: history'usage in frontend routing
            LOGGER.info("URL {} entered directly into the Browser, redirecting...", req.getRequestURI());
            RequestDispatcher rd = req.getRequestDispatcher("/");
            rd.forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}
