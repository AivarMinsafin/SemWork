package ru.itis.aivar.servlets;

import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.SecurityService;
import ru.itis.aivar.services.abstracts.UsersService;
import ru.itis.aivar.utils.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {
    private UsersService usersService;
    private SecurityService securityService;
    private List<String> errors = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errors", errors);
        getServletContext().getRequestDispatcher("/jsp/update-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newNickname = req.getParameter("nickname");
        String newEmail = req.getParameter("email");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("conf_password");
        User user = (User) req.getSession().getAttribute("user");
        if (newPassword.equals(confirmPassword)
                && Validator.validateEmail(newEmail)
                && Validator.validatePassword(newPassword)
                && Validator.validateLogin(newNickname)
        ){
            User updatedUser = User.builder()
                    .id(user.getId())
                    .nickname(newNickname)
                    .email(newEmail)
                    .hashPassword(securityService.encode(newPassword))
                    .build();
            boolean updated = usersService.update(updatedUser);
            if (updated){
                req.getSession().setAttribute("user", updatedUser);
                resp.sendRedirect(getServletContext().getContextPath()+"/profile");
                return;
            } else {
                errors.add("None unique data");
            }
        } else {
            errors.add("Not valid data");
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/");
    }
}
