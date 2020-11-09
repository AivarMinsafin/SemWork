package ru.itis.aivar.services.abstracts;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;

import javax.servlet.http.Part;
import java.io.OutputStream;
import java.util.List;

public interface PageService {
    Page getPageById(Long id);
    List<Page> getPagesByChapter(Chapter chapter);
    void writeToOutputStream(Page page, OutputStream outputStream);
    String getPagePath(Page page);
    Page save(Page page, Part part);
    void deletePages(Chapter chapter);
}
