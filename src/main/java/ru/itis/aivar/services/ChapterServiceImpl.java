package ru.itis.aivar.services;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.repositories.abstracts.ChapterRepository;
import ru.itis.aivar.repositories.abstracts.filerepo.ChapterRepositoryFileSystem;
import ru.itis.aivar.services.abstracts.ChapterService;
import ru.itis.aivar.services.abstracts.TitleService;

import java.util.List;

public class ChapterServiceImpl implements ChapterService {

    private ChapterRepository chapterRepository;
    private ChapterRepositoryFileSystem chapterRepositoryFileSystem;
    private TitleService titleService;

    public ChapterServiceImpl(ChapterRepository chapterRepository, ChapterRepositoryFileSystem chapterRepositoryFileSystem, TitleService titleService) {
        this.chapterRepository = chapterRepository;
        this.chapterRepositoryFileSystem = chapterRepositoryFileSystem;
        this.titleService = titleService;
    }

    @Override
    public List<Chapter> getChaptersByTitle(Title title) {
        return chapterRepository.findAllChaptersByTitleId(title.getId());
    }

    @Override
    public Chapter getChapterById(Long id) {
        return chapterRepository.findById(id).orElse(null);
    }

    @Override
    public Chapter save(Chapter chapter) {
        chapter = chapterRepository.save(chapter);
        chapterRepositoryFileSystem.save(chapter);
        return chapter;
    }

    @Override
    public Chapter getMaxChapter(Title title) {
        return chapterRepository.findMaxChapterByTitle(title).orElse(null);
    }

    @Override
    public Chapter findByChapterNum(int num, Title title) {
        return chapterRepository.findChapterByChapterNumber(num, title).orElse(null);
    }

    @Override
    public void update(Chapter update) {
        chapterRepository.update(update);
    }

    @Override
    public void delete(Chapter chapter) {
        chapterRepositoryFileSystem.delete(chapter);
        chapterRepository.delete(chapter);
    }
}
