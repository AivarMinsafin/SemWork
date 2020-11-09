package ru.itis.aivar.services.abstracts;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;

import java.util.List;

public interface ChapterService {

    List<Chapter> getChaptersByTitle(Title title);
    Chapter getChapterById(Long id);
    Chapter save(Chapter chapter);
    Chapter getMaxChapter(Title title);
    Chapter findByChapterNum(int num, Title title);
    void update(Chapter update);
    void delete(Chapter chapter);
}
