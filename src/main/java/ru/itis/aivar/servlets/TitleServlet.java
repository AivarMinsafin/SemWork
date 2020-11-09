package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.ChapterService;
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
import java.util.stream.Collectors;

@WebServlet("/title")
public class TitleServlet extends HttpServlet {
    TitleService titleService;
    ChapterService chapterService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        chapterService = (ChapterService) servletContext.getAttribute("chapterService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        User user = (User) req.getSession().getAttribute("user");
        Long id;
        if (idStr != null){
            id = Long.valueOf(idStr);
        } else {
            id = null;
        }
        Title title;
        if (id != null){
            title = titleService.getTitleById(id);
        } else {
            title = null;
        }
        if (title != null){
            List<Chapter> chapters = chapterService.getChaptersByTitle(title);
            boolean showAdd = false;
            if (user != null){
                List<Long> titlesIds = titleService.findByUser(user).stream().map(Title::getId).collect(Collectors.toList());
                showAdd = !titlesIds.contains(title.getId());
            }
            req.setAttribute("showAdd", showAdd);
            req.setAttribute("title", title);
            req.setAttribute("chapters", chapters);
            getServletContext().getRequestDispatcher("/jsp/title.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/main");
    }
}
