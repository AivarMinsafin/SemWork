package ru.itis.aivar.repositories.abstracts.filerepo;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;

import javax.servlet.http.Part;
import java.io.OutputStream;

public interface PageRepositoryFileSystem {

    void writeToOutputStream(Page page, OutputStream outputStream);
    String getPagePath(Page page);
    void save(Page page, Part part);
    void deletePages(Chapter chapter);

}
