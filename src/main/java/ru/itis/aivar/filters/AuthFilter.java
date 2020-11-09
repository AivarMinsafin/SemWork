package ru.itis.aivar.filters;

import ru.itis.aivar.services.abstracts.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/profile")
public class AuthFilter extends HttpFilter {

    SecurityService securityService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (!securityService.isSigned(req)){
            res.sendRedirect(req.getContextPath().concat("/auth"));
            return;
        }
        chain.doFilter(req, res);
    }
}
