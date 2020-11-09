package ru.itis.aivar.repositories;

import ru.itis.aivar.exceptions.db.NoneUniqueValueException;
import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.User;
import ru.itis.aivar.repositories.abstracts.AuthorRepository;
import ru.itis.aivar.repositories.abstracts.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryJdbcImpl implements AuthorRepository {
    //language=sql
    private static final String SQL_FIND_BY_USER_ID = "select * from author where user_account = ?";
    //language=sql
    private static final String SQL_INSERT_AUTHOR = "insert into author (first_name, last_name, user_account) values (?,?,?) returning id";
    //language=sql
    private static final String SQL_UPDATE_AUTHOR = "update author set first_name = ?, last_name = ? where id = ?";
    //language=sql
    private static final String SQL_DELETE_AUTHOR = "delete from author where id = ?";
    //language=sql
    private static final String SQL_FIND_BY_ID = "select * from author where id=?";
    //language=sql
    private static final String SQL_FIND_ALL = "select * from author";

    private DataSource dataSource;
    private SimpleJdbcTemplate<Author> simpleJdbcTemplate;
    private RowMapper<Author> authorRowMapper = resultSet -> {
        Author author = Author.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .user(User.builder()
                        .id((long) resultSet.getInt("user_account"))
                        .build())
                .build();
        return author;
    };

    public AuthorRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate<>(dataSource);
    }

    @Override
    public Author save(Author entity) {
        Author author = entity;
        try {
            Long id = simpleJdbcTemplate.queryInsertReturningId(
                    SQL_INSERT_AUTHOR,
                    author.getFirstName(),
                    author.getLastName(),
                    author.getUser().getId()
            );
            author.setId(id);
        } catch (NoneUniqueValueException e) {
            author = null;
        }
        return author;
    }

    @Override
    public void update(Author entity) {
        simpleJdbcTemplate.query(
                SQL_UPDATE_AUTHOR,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getId()
        );
    }

    @Override
    public void delete(Author entity) {
        simpleJdbcTemplate.query(SQL_DELETE_AUTHOR, entity.getId());
    }

    @Override
    public Optional<Author> findById(Long id) {
        Author author;
        try {
            List<Author> authors = simpleJdbcTemplate.query(SQL_FIND_BY_ID, authorRowMapper, id);
            if (!authors.isEmpty()) {
                author = authors.get(0);
            } else {
                author = null;
            }
        } catch (IllegalStateException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        return simpleJdbcTemplate.query(SQL_FIND_ALL, authorRowMapper);
    }

    @Override
    public Optional<Author> findByUser(User user) {
        Long userId = user.getId();
        Author author;
        try {
            List<Author> authors = simpleJdbcTemplate.query(SQL_FIND_BY_USER_ID, authorRowMapper, userId);
            if (!authors.isEmpty()) {
                author = authors.get(0);
            } else {
                author = null;
            }
        } catch (IllegalStateException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }
}
