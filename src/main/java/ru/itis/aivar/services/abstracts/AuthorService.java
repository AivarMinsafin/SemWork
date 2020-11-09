package ru.itis.aivar.services.abstracts;

import ru.itis.aivar.dto.AuthorDto;
import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.User;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author findById(Long id);
    User findUserByAuthor(Author author);
    List<AuthorDto> getAllAuthorsDto();
    AuthorDto findDtoById(Long id);
    boolean isAuthor(User user);
    Author findByUser(User user);
    Author save(Author author);
}
