package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.services.abstracts.ChapterService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chapter-delete")
public class DeleteChapter extends HttpServlet {
    ChapterService chapterService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        chapterService = (ChapterService) servletContext.getAttribute("chapterService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chapterId = req.getParameter("id");
        Chapter chapter = chapterService.getChapterById(Long.valueOf(chapterId));
        chapterService.delete(chapter);
        resp.sendRedirect(getServletContext().getContextPath()+"/title-manager");
    }
}
