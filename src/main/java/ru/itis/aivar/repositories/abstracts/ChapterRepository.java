package ru.itis.aivar.repositories.abstracts;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends CrudRepository<Chapter>{

    List<Chapter> findAllChaptersByTitleId(Long id);

    Optional<Chapter> findMaxChapterByTitle(Title title);

    Optional<Chapter> findChapterByChapterNumber(int num, Title title);


}
