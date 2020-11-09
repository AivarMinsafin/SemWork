package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.AuthorService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/new-author")
public class AuthorCreationServlet extends HttpServlet {

    private AuthorService authorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        authorService = (AuthorService) servletContext.getAttribute("authorService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/become-an-author.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User user = (User) req.getSession().getAttribute("user");
        if (firstName.length() > 1 && lastName.length() > 1){
            Author author = Author.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .user(user)
                    .build();
            author = authorService.save(author);
            getServletContext().getRequestDispatcher("/jsp/author-success.jsp").forward(req, resp);
        }
    }
}
