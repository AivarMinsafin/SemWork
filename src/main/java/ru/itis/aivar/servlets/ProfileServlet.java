package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.AuthorService;
import ru.itis.aivar.services.abstracts.TitleService;
import ru.itis.aivar.services.abstracts.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private TitleService titleService;
    private AuthorService authorService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        authorService = (AuthorService) servletContext.getAttribute("authorService");
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        boolean isAuthor = authorService.isAuthor(user);
        List<Title> titles = titleService.findByUser(user);
        req.setAttribute("isAuthor", isAuthor);
        req.setAttribute("titles", titles);
        getServletContext().getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}
