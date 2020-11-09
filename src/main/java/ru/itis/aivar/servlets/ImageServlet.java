package ru.itis.aivar.servlets;

import ru.itis.aivar.models.Page;
import ru.itis.aivar.services.abstracts.PageService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    private String titlesDir;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        titlesDir = (String) servletContext.getAttribute("titlesDir");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqImage = req.getPathInfo();
        if (reqImage == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        File image = new File(titlesDir, URLDecoder.decode(reqImage, "UTF-8"));
        if (!image.exists()){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String contentType = getServletContext().getMimeType(image.getName());
        if (contentType == null || !contentType.startsWith("image")){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.reset();
        resp.setContentType(contentType);
        resp.setHeader("Content-Length", String.valueOf(image.length()));
        Files.copy(image.toPath(), resp.getOutputStream());
    }
}
