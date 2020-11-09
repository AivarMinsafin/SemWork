package ru.itis.aivar.repositories.abstracts.filerepo;

import ru.itis.aivar.models.Title;

import javax.servlet.http.Part;

public interface TitleRepositoryFileSystem {
    void save(Title title, Part part);
    void delete(Title title);
    void update(Title title, Part part);
}
