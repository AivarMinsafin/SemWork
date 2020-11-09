package ru.itis.aivar.servlets;

import ru.itis.aivar.services.abstracts.SecurityService;
import ru.itis.aivar.services.abstracts.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    UsersService usersService;
    SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("userService");
        this.securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/auth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean rememberMe =req.getParameter("remember") != null && req.getParameter("remember").equals("on");
        if (securityService.singIn(req, email, password, rememberMe)){
            resp.sendRedirect(getServletContext().getContextPath()+"/profile");
            return;
        }
        req.setAttribute("email", req.getParameter("email"));
        getServletContext().getRequestDispatcher("/jsp/auth.jsp").forward(req, resp);
    }
}
