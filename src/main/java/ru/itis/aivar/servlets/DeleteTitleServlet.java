package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.services.abstracts.ChapterService;
import ru.itis.aivar.services.abstracts.PageService;
import ru.itis.aivar.services.abstracts.TitleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/title-delete")
public class DeleteTitleServlet extends HttpServlet {

    private TitleService titleService;
    private ChapterService chapterService;
    private PageService pageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        pageService = (PageService) servletContext.getAttribute("pageService");
        chapterService = (ChapterService) servletContext.getAttribute("chapterService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titleId = req.getParameter("id");
        Title title = titleService.getTitleById(Long.valueOf(titleId));
        titleService.delete(title);
        resp.sendRedirect(getServletContext().getContextPath()+"/title-manager");
    }
}
