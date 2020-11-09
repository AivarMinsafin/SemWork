package ru.itis.aivar.repositories.abstracts;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title>{
    Optional<Title> findByTitle(String title);
    List<Title> findByUser(User user);
    List<Title> findByAuthor(Author author);
}
