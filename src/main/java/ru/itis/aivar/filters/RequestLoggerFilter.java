package ru.itis.aivar.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter("/*")
public class RequestLoggerFilter extends HttpFilter {

    Logger logger;

    @Override
    public void init() throws ServletException {
        logger = LoggerFactory.getLogger(RequestLoggerFilter.class);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info(req.getMethod().concat(" ").concat(req.getRequestURI()));
        chain.doFilter(req, res);
    }
}
