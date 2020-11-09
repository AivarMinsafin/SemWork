package ru.itis.aivar.services;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.repositories.abstracts.TitleRepository;
import ru.itis.aivar.repositories.abstracts.filerepo.TitleRepositoryFileSystem;
import ru.itis.aivar.services.abstracts.AuthorService;
import ru.itis.aivar.services.abstracts.TitleService;

import javax.servlet.http.Part;
import java.nio.file.Paths;
import java.util.List;

public class TitleServiceImpl implements TitleService {
    private TitleRepository titleRepository;
    private AuthorService authorService;
    private TitleRepositoryFileSystem titleRepositoryFileSystem;

    public TitleServiceImpl(TitleRepository titleRepository, AuthorService authorService, TitleRepositoryFileSystem titleRepositoryFileSystem) {
        this.titleRepository = titleRepository;
        this.authorService = authorService;
        this.titleRepositoryFileSystem = titleRepositoryFileSystem;
    }

    @Override
    public List<Title> getAllTitles() {
        List<Title> titles = titleRepository.findAll();
        for (Title t : titles) {
            Author author = authorService.findById(t.getAuthor().getId());
            if (author != null) {
                t.setAuthor(author);
            }
        }
        return titles;
    }

    @Override
    public Title getTitleById(Long id) {
        Title title = titleRepository.findById(id).orElse(null);
        if (title != null) {
            Author author = authorService.findById(title.getAuthor().getId());
            if (author != null) {
                title.setAuthor(author);
            }
        }
        return title;
    }

    @Override
    public Title getTitleByTitle(String title) {
        return titleRepository.findByTitle(title).orElse(null);
    }

    @Override
    public Title save(Title title, Part image) {
        title = titleRepository.save(title);
        title.setTitleImg(String.valueOf(Paths.get(String.valueOf(title.getId())).resolve(image.getSubmittedFileName())));
        titleRepository.update(title);
        titleRepositoryFileSystem.save(title, image);
        return title;
    }

    @Override
    public List<Title> findByUser(User user) {
        return titleRepository.findByUser(user);
    }

    @Override
    public void delete(Title title) {
        titleRepositoryFileSystem.delete(title);
        titleRepository.delete(title);
    }

    @Override
    public List<Title> findByAuthor(Author author) {
        return titleRepository.findByAuthor(author);
    }

    @Override
    public void update(Title title, Part part) {
        if (part != null){
            titleRepositoryFileSystem.update(title, part);
            title.setTitleImg(String.valueOf(Paths.get(String.valueOf(title.getId())).resolve(part.getSubmittedFileName())));
            titleRepository.update(title);
        } else {
            titleRepository.update(title);
        }
    }
}
