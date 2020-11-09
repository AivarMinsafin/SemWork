package ru.itis.aivar.servlets;

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

@WebServlet("/create-title")
@MultipartConfig
public class CreateTitleServlet extends HttpServlet {
    private TitleService titleService;
    private AuthorService authorService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titleService = (TitleService) servletContext.getAttribute("titleService");
        authorService = (AuthorService) servletContext.getAttribute("authorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/create-title.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titleName = req.getParameter("titleName");
        String description = req.getParameter("description")==null?"": req.getParameter("description");
        User user = (User) req.getSession().getAttribute("user");
        Part titleImage = req.getPart("file");
        if (titleImage.getSize() > 0){
            Title title = Title.builder()
                    .title(titleName)
                    .description(description)
                    .author(authorService.findByUser(user))
                    .build();
            title = titleService.save(title, titleImage);
            if (title != null){
                resp.sendRedirect(getServletContext().getContextPath()+"/title-manager)");
                return;
            }
        } else {
            resp.sendRedirect(getServletContext().getContextPath()+"/create-title)");
            return;
        }
    }
}
