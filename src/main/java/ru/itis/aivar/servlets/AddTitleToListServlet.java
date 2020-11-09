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

@WebServlet("/add-to-list")
public class AddTitleToListServlet extends HttpServlet {
    TitleService titleService;
    UsersService usersService;
    AuthorService authorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        usersService = (UsersService) servletContext.getAttribute("usersService");
        authorService = (AuthorService) servletContext.getAttribute("authorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titleId = req.getParameter("id");
        User user = (User) req.getSession().getAttribute("user");
        Title title = titleService.getTitleById(Long.valueOf(titleId));
        List<Title> titles = titleService.findByUser(user);
        if (titles.contains(title)) {
            resp.sendRedirect(getServletContext().getContextPath() + "/title?id=" + title.getId());
            return;
        }
        usersService.addTitle(user, title);
        resp.sendRedirect(getServletContext().getContextPath() + "/title?id=" + title.getId());
        return;
    }
}
