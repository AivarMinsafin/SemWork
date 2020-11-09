package ru.itis.aivar.services.abstracts;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;

import javax.servlet.http.Part;
import java.util.List;

public interface TitleService {
    List<Title> getAllTitles();
    Title getTitleById(Long id);
    Title getTitleByTitle(String title);
    Title save(Title title, Part part);
    List<Title> findByUser(User user);
    void delete(Title title);
    List<Title> findByAuthor(Author author);
    void update(Title title, Part part);
}
