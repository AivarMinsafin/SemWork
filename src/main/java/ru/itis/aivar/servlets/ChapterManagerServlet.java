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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/chapter-manager")
@MultipartConfig
public class ChapterManagerServlet extends HttpServlet {
    TitleService titleService;
    ChapterService chapterService;
    PageService pageService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        chapterService = (ChapterService) servletContext.getAttribute("chapterService");
        pageService = (PageService) servletContext.getAttribute("pageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titleId = req.getParameter("id");
        String chapterId = req.getParameter("chapter");
        Title title = titleService.getTitleById(Long.valueOf(titleId));
        if (chapterId == null) {
            List<Chapter> chapters = chapterService.getChaptersByTitle(title);
            req.setAttribute("title", title);
            req.setAttribute("chapters", chapters);
            getServletContext().getRequestDispatcher("/jsp/chapter-manager.jsp").forward(req, resp);
            return;
        } else {
            Chapter chapter = chapterService.getChapterById(Long.valueOf(chapterId));
            req.setAttribute("chapter", chapter);
            req.setAttribute("title", title);
            getServletContext().getRequestDispatcher("/jsp/chapter-manager-edit.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chapterTitle = req.getParameter("title");
        String titleObj_id = req.getParameter("id");
        String chapterId = req.getParameter("chapter_id");
        Chapter chapter = chapterService.getChapterById(Long.valueOf(chapterId));
        Title title = titleService.getTitleById(Long.valueOf(titleObj_id));
        List<Part> parts = req.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
        Chapter upChapter = Chapter.builder()
                .chapterTitle(chapterTitle)
                .id(chapter.getId())
                .title(title)
                .number(chapter.getNumber())
                .build();
        chapterService.update(upChapter);
        if (!parts.isEmpty()){
            pageService.deletePages(upChapter);
            int pNum = 1;
            for (Part part : parts) {
                Page page = Page.builder()
                        .pageNumber(pNum)
                        .chapter(chapter)
                        .build();
                pNum++;
                page = pageService.save(page, part);
            }
            resp.sendRedirect(getServletContext().getContextPath()+"/chapter-manager?id="+title.getId());
            return;
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/chapter-manager?id="+title.getId());
        return;
    }
}
