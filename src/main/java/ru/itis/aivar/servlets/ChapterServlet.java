package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;
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
import java.util.List;

@WebServlet("/chapter")
public class ChapterServlet extends HttpServlet {
    PageService pageService;
    TitleService titleService;
    ChapterService chapterService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        pageService = (PageService) servletContext.getAttribute("pageService");
        titleService = (TitleService) servletContext.getAttribute("titleService");
        chapterService = (ChapterService) servletContext.getAttribute("chapterService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title_id = req.getParameter("title");
        String chapterNum = req.getParameter("chapter_num");
        Title title;
        if (title_id != null){
            title = titleService.getTitleById(Long.valueOf(title_id));
            if (title != null){
                if (chapterNum != null){
                    Chapter chapter = chapterService.findByChapterNum(Integer.parseInt(chapterNum), title);
                    if (chapter != null){
                        List<Page> pages = pageService.getPagesByChapter(chapter);
                        req.setAttribute("pages", pages);
                        req.setAttribute("chapter", chapter);
                        req.setAttribute("title", title);
                        getServletContext().getRequestDispatcher("/jsp/chapter.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect(getServletContext().getContextPath()+"/title?id="+title.getId());
                        return;
                    }
                } else {
                    resp.sendRedirect(getServletContext().getContextPath()+"/title?id="+title.getId());
                    return;
                }
            } else {
                resp.sendRedirect(getServletContext().getContextPath()+"/main");
                return;
            }
        } else {
            resp.sendRedirect(getServletContext().getContextPath()+"/main");
            return;
        }
    }
}
