package ru.itis.aivar.repositories;

import ru.itis.aivar.exceptions.db.NoneUniqueValueException;
import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;
import ru.itis.aivar.repositories.abstracts.PageRepository;
import ru.itis.aivar.repositories.abstracts.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class PageRepositoryJdbcImpl implements PageRepository {
    //language=sql
    private static final String SQL_DELETE_BY_CHAPTER = "delete from page where chapter_id=?";
    //language=sql
    private static final String SQL_FIND_ALL = "select * from page";
    //language=sql
    private static final String SQL_FIND_BY_CHAPTER = "select * from page where chapter_id=? order by page_number";
    //language=sql
    private static final String SQL_INSERT = "insert into page (chapter_id, page_number, image_path) values (?,?,?) returning id";
    //language=sql
    private static final String SQL_UPDATE = "update page set image_path=? where id=?";
    //language=sql
    private static final String SQL_DELETE = "delete from page where id = ?";

    private DataSource dataSource;
    private SimpleJdbcTemplate<Page> simpleJdbcTemplate;
    private RowMapper<Page> pageRowMapper = resultSet -> {
        Page page = Page.builder()
                .id(resultSet.getLong("id"))
                .pageNumber(resultSet.getInt("page_number"))
                .imagePath(resultSet.getString("image_path"))
                .chapter(Chapter.builder()
                        .id(resultSet.getLong("chapter_id"))
                        .build())
                .build();
        return page;
    };

    public PageRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate<>(dataSource);
    }

    @Override
    public List<Page> getAllPagesByChapter(Chapter chapter) {
        return simpleJdbcTemplate.query(SQL_FIND_BY_CHAPTER, pageRowMapper, chapter.getId());
    }

    @Override
    public void deleteByChapter(Chapter chapter) {
        simpleJdbcTemplate.query(SQL_DELETE_BY_CHAPTER, chapter.getId());
    }

    @Override
    public Page save(Page entity) {
        Page page = entity;
        try {
            Long id = simpleJdbcTemplate.queryInsertReturningId(
                    SQL_INSERT,
                    page.getChapter().getId(),
                    page.getPageNumber(),
                    page.getImagePath()
            );
            page.setId(id);
        } catch (NoneUniqueValueException e) {
            page = null;
        }
        return page;
    }

    @Override
    public void update(Page entity) {
        simpleJdbcTemplate.query(SQL_UPDATE, entity.getImagePath(), entity.getId());
    }

    @Override
    public void delete(Page entity) {
        simpleJdbcTemplate.query(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<Page> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Page> findAll() {
        return simpleJdbcTemplate.query(SQL_FIND_ALL, pageRowMapper);
    }
}
