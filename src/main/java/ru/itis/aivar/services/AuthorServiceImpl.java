package ru.itis.aivar.services;

import ru.itis.aivar.dto.AuthorDto;
import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.User;
import ru.itis.aivar.repositories.abstracts.AuthorRepository;
import ru.itis.aivar.repositories.abstracts.UsersRepository;
import ru.itis.aivar.services.abstracts.AuthorService;
import ru.itis.aivar.services.abstracts.UsersService;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {
    AuthorRepository authorRepository;
    UsersService usersService;

    public AuthorServiceImpl(AuthorRepository authorRepository, UsersService usersService) {
        this.authorRepository = authorRepository;
        this.usersService = usersService;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        for (Author a : authors) {
            User user = usersService.findById(a.getUser().getId());
            if (user != null){
                a.setUser(user);
            }
        }
        return authors;
    }

    @Override
    public Author findById(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null){
            User user = usersService.findById(author.getUser().getId());
            if (user != null){
                author.setUser(user);
            }
        }
        return author;
    }

    @Override
    public User findUserByAuthor(Author author) {
        return null;
    }

    @Override
    public List<AuthorDto> getAllAuthorsDto() {
        return AuthorDto.from(authorRepository.findAll());
    }

    @Override
    public AuthorDto findDtoById(Long id) {
        Author author = findById(id);
        if (author != null){
            return AuthorDto.from(author);
        }
        return null;
    }

    @Override
    public boolean isAuthor(User user) {
        return findByUser(user) != null;
    }

    @Override
    public Author findByUser(User user) {
        Optional<Author> optionalAuthor = authorRepository.findByUser(user);
        return optionalAuthor.orElse(null);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
