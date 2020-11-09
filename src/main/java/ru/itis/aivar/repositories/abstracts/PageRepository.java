package ru.itis.aivar.repositories.abstracts;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;

import java.util.List;

public interface PageRepository extends CrudRepository<Page>{

    List<Page> getAllPagesByChapter(Chapter chapter);
    void deleteByChapter(Chapter chapter);
}
