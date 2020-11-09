package ru.itis.aivar.repositories.abstracts;

import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.User;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author>{
    Optional<Author> findByUser(User user);
}
