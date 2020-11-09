package ru.itis.aivar.repositories.abstracts.filerepo;

import ru.itis.aivar.models.Chapter;

public interface ChapterRepositoryFileSystem {
    void save(Chapter chapter);
    void delete(Chapter chapter);
}
