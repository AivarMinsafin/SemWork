package ru.itis.aivar.servlets;

import ru.itis.aivar.services.abstracts.SecurityService;

import static ru.itis.aivar.utils.Validator.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/signup")
public class SingUpServlet extends HttpServlet {
    SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        this.securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String nickname = req.getParameter("login");
        String password = req.getParameter("password");
        boolean checkBox = req.getParameter("agreement").equals("on");
        if (checkBox){
            if (validatePassword(password) && validateEmail(email) && validateLogin(nickname)){
                if (securityService.signUp(req, email, nickname, password)){
                    resp.sendRedirect(getServletContext().getContextPath()+"/profile");
                } else {
                    req.setAttribute("message", "User with this email exists");
                    getServletContext().getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("message", "Not valid data");
                getServletContext().getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("message", "You should agree with out terms");
            getServletContext().getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);
        }
    }
}
