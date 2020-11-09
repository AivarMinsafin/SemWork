package ru.itis.aivar.services;

import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;
import ru.itis.aivar.repositories.abstracts.PageRepository;
import ru.itis.aivar.repositories.abstracts.filerepo.PageRepositoryFileSystem;
import ru.itis.aivar.services.abstracts.ChapterService;
import ru.itis.aivar.services.abstracts.PageService;

import javax.servlet.http.Part;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

public class PageServiceImpl implements PageService {

    private PageRepository pageRepository;
    private PageRepositoryFileSystem pageRepositoryFileSystem;
    private ChapterService chapterService;

    public PageServiceImpl(PageRepository pageRepository, PageRepositoryFileSystem pageRepositoryFileSystem, ChapterService chapterService) {
        this.pageRepository = pageRepository;
        this.pageRepositoryFileSystem = pageRepositoryFileSystem;
        this.chapterService = chapterService;
    }

    @Override
    public Page getPageById(Long id) {
        Page page = pageRepository.findById(id).orElse(null);
        if (page != null){
            Chapter chapter = chapterService.getChapterById(page.getChapter().getId());
            if (chapter != null){
                page.setChapter(chapter);
            }
        }
        return page;
    }

    @Override
    public List<Page> getPagesByChapter(Chapter chapter) {
        return pageRepository.getAllPagesByChapter(chapter);
    }

    @Override
    public void writeToOutputStream(Page page, OutputStream outputStream) {
        pageRepositoryFileSystem.writeToOutputStream(page, outputStream);
    }

    @Override
    public String getPagePath(Page page) {
        return pageRepositoryFileSystem.getPagePath(page);
    }

    @Override
    public Page save(Page page, Part part) {
        page.setImagePath(String.valueOf(Paths.get(String.valueOf(page.getChapter().getTitle().getId()))
                .resolve(String.valueOf(page.getChapter().getId()))
                .resolve(part.getSubmittedFileName())));
        page = pageRepository.save(page);
        pageRepositoryFileSystem.save(page, part);
        return page;
    }

    @Override
    public void deletePages(Chapter chapter) {
        pageRepositoryFileSystem.deletePages(chapter);
        pageRepository.deleteByChapter(chapter);
    }
}
