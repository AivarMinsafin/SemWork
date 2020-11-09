package ru.itis.aivar.repositories;

import ru.itis.aivar.exceptions.db.NoneUniqueValueException;
import ru.itis.aivar.models.Author;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.repositories.abstracts.RowMapper;
import ru.itis.aivar.repositories.abstracts.TitleRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class TitleRepositoryJdbcImpl implements TitleRepository {
    //language=sql
    private static final String SQL_FIND_BY_AUTHOR = "select * from title where author_id = ?";
    //language=sql
    private static final String SQL_FIND_BY_USER = "select t.id, t.title, t.author_id, t.title_image, t.description from user_title ut left join title t on t.id = ut.title_id and ut.user_id = ?;";
    //language=sql
    private static final String SQL_FIND_BY_TITLE = "select * from title where title.title = ?";
    //language=sql
    private static final String SQL_FIND_BY_ID = "select * from title where id = ?";
    //language=sql
    private static final String SQL_UPDATE_TITLE = "update title set title = ?, title_image = ?, description = ? where id = ?";
    //language=sql
    private static final String SQL_DELETE_TITLE = "delete from title where id=?";
    //language=sql
    private static final String SQL_FIND_ALL = "select * from title";
    //language=SQL
    private static final String SQL_INSERT_TITLE = "insert into title (title, author_id, title_image, description) values (?, ?, ?, ?) returning id";

    private DataSource dataSource;
    private SimpleJdbcTemplate<Title> simpleJdbcTemplate;

    private RowMapper<Title> titleRowMapper = resultSet -> {
        Title title = Title.builder()
                .id(resultSet.getLong(1))
                .title(resultSet.getString(2))
                .author(Author.builder()
                        .id(resultSet.getLong(3))
                        .build())
                .titleImg(resultSet.getString(4))
                .description(resultSet.getString(5))
                .build();
        return title;
    };

    public TitleRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate<>(dataSource);
    }

    @Override
    public Title save(Title entity) {
        Title title = entity;
        try {
            Long id = simpleJdbcTemplate.queryInsertReturningId(
                    SQL_INSERT_TITLE,
                    title.getTitle(),
                    title.getAuthor().getId(),
                    title.getTitleImg(),
                    title.getDescription()
            );
            title.setId(id);
        } catch (NoneUniqueValueException e) {
            title = null;
        }
        return title;
    }

    @Override
    public void update(Title entity) {
        simpleJdbcTemplate.query(
                SQL_UPDATE_TITLE,
                entity.getTitle(),
                entity.getTitleImg(),
                entity.getDescription(),
                entity.getId()
        );
    }

    @Override
    public void delete(Title entity) {
        simpleJdbcTemplate.query(SQL_DELETE_TITLE, entity.getId());
    }

    @Override
    public Optional<Title> findById(Long id) {
        Title title;
        try {
            List<Title> titles = simpleJdbcTemplate.query(SQL_FIND_BY_ID, titleRowMapper, id);
            if (!titles.isEmpty()) {
                title = titles.get(0);
            } else {
                title = null;
            }
        } catch (IllegalStateException e) {
            title = null;
        }
        return Optional.ofNullable(title);
    }

    @Override
    public List<Title> findAll() {
        return simpleJdbcTemplate.query(SQL_FIND_ALL, titleRowMapper);
    }


    @Override
    public Optional<Title> findByTitle(String title) {
        Title titleObj;
        try {
            List<Title> titles = simpleJdbcTemplate.query(SQL_FIND_BY_TITLE, titleRowMapper, title);
            if (!titles.isEmpty()) {
                titleObj = titles.get(0);
            } else {
                titleObj = null;
            }
        } catch (IllegalStateException e) {
            titleObj = null;
        }
        return Optional.ofNullable(titleObj);
    }

    @Override
    public List<Title> findByUser(User user) {
        return simpleJdbcTemplate.query(SQL_FIND_BY_USER, titleRowMapper, user.getId());
    }

    @Override
    public List<Title> findByAuthor(Author author) {
        return simpleJdbcTemplate.query(SQL_FIND_BY_AUTHOR, titleRowMapper, author.getId());
    }
}
