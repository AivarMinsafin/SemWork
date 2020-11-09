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

@WebServlet("/add-chapter")
@MultipartConfig
public class AddChapterServlet extends HttpServlet {
    ChapterService chapterService;
    TitleService titleService;
    PageService pageService;

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
        String id = req.getParameter("id");
        Title title = titleService.getTitleById(Long.valueOf(id));
        req.setAttribute("title", title);
        getServletContext().getRequestDispatcher("/jsp/add-chapter.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chapterTitle = req.getParameter("title");
        String titleObj_id = req.getParameter("id");
        Title title = titleService.getTitleById(Long.valueOf(titleObj_id));
        Chapter maxCh = chapterService.getMaxChapter(title);
        int maxChapter = maxCh != null ? maxCh.getNumber() : 0;
        List<Part> parts = req.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
        if (!parts.isEmpty()){
            Chapter chapter = Chapter.builder()
                    .chapterTitle(chapterTitle)
                    .title(title)
                    .number(maxChapter + 1)
                    .build();
            chapter = chapterService.save(chapter);
            int pNum = 1;
            for (Part part : parts) {
                Page page = Page.builder()
                        .pageNumber(pNum)
                        .chapter(chapter)
                        .build();
                pNum++;
                page = pageService.save(page, part);
            }
            resp.sendRedirect(getServletContext().getContextPath()+"/title-manager?id="+title.getId());
            return;
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/add-chapter?id="+title.getId());
    }
}
