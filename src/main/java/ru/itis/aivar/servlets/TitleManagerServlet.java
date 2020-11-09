package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.AuthorService;
import ru.itis.aivar.services.abstracts.TitleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/title-manager")
@MultipartConfig
public class TitleManagerServlet extends HttpServlet {
    TitleService titleService;
    AuthorService authorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        authorService = (AuthorService) servletContext.getAttribute("authorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            Author author = authorService.findByUser((User) req.getSession().getAttribute("user"));
            List<Title> titles = titleService.findByAuthor(author);
            req.setAttribute("titles", titles);
            getServletContext().getRequestDispatcher("/jsp/title-manager.jsp").forward(req, resp);
            return;
        } else {
            Long id = Long.valueOf(idStr);
            Title title = titleService.getTitleById(id);
            User author = title.getAuthor().getUser();
            if (author.equals(req.getSession().getAttribute("user"))) {
                req.setAttribute("title", title);
                getServletContext().getRequestDispatcher("/jsp/title-manager-edit.jsp").forward(req, resp);
                return;
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String titleName = req.getParameter("title");
        String description = req.getParameter("description");
        Part part = req.getPart("file");
        if (!(part.getSize() > 0)) {
            part = null;
        }
        Title title = titleService.getTitleById(Long.valueOf(id));
        if (title != null){
            title.setTitle(titleName);
            title.setDescription(description);
            titleService.update(title, part);
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/title-manager");
        return;
    }
}
