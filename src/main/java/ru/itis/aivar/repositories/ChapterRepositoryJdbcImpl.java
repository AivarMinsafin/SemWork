package ru.itis.aivar.repositories;

import ru.itis.aivar.exceptions.db.NoneUniqueValueException;
import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.repositories.abstracts.ChapterRepository;
import ru.itis.aivar.repositories.abstracts.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ChapterRepositoryJdbcImpl implements ChapterRepository {
    //language=sql
    private static final String SQL_FIND_BY_NUMBER = "select * from chapter where title_id = ? and chapter_number = ?";
    //language=sql
    private static final String SQL_FIND_MAX_CHAPTER = "select * from chapter where title_id = ? order by chapter_number desc limit 1";
    //language=sql
    private static final String SQL_FIND_BY_ID = "select * from chapter where id = ?";
    //language=sql
    private static final String SQL_DELETE = "delete from chapter where id = ?";
    //language=sql
    private static final String SQL_UPDATE = "update chapter set title=?, chapter_number=? where id = ?";
    //language=sql
    private static final String SQL_FIND_ALL_BY_TITLE = "select * from chapter where title_id = ? order by chapter_number";
    //language=sql
    private static final String SQL_INSERT = "insert into chapter (title, chapter_number, title_id) values (?,?,?) returning id";

    private DataSource dataSource;
    private SimpleJdbcTemplate<Chapter> simpleJdbcTemplate;
    private RowMapper<Chapter> chapterRowMapper = resultSet -> {
        Chapter chapter = Chapter.builder()
                .id(resultSet.getLong("id"))
                .chapterTitle(resultSet.getString("title"))
                .number(resultSet.getInt("chapter_number"))
                .title(Title.builder()
                        .id(resultSet.getLong("title_id"))
                        .build())
                .build();
        return chapter;
    };

    public ChapterRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate<>(dataSource);
    }

    @Override
    public List<Chapter> findAllChaptersByTitleId(Long id) {
        return simpleJdbcTemplate.query(SQL_FIND_ALL_BY_TITLE, chapterRowMapper, id);
    }

    @Override
    public Optional<Chapter> findMaxChapterByTitle(Title title) {
        Chapter chapter;
        List<Chapter> chapters = simpleJdbcTemplate.query(SQL_FIND_MAX_CHAPTER, chapterRowMapper, title.getId());
        if (!chapters.isEmpty()){
            chapter = chapters.get(0);
        } else {
            chapter = null;
        }
        return Optional.ofNullable(chapter);
    }

    @Override
    public Optional<Chapter> findChapterByChapterNumber(int num, Title title) {
        Chapter chapter;
        try {
            List<Chapter> chapters = simpleJdbcTemplate.query(SQL_FIND_BY_NUMBER, chapterRowMapper, title.getId(), num);
            if (!chapters.isEmpty()) {
                chapter = chapters.get(0);
            } else {
                chapter = null;
            }
        } catch (IllegalStateException e) {
            chapter = null;
        }
        return Optional.ofNullable(chapter);
    }

    @Override
    public Chapter save(Chapter entity) {
        Chapter chapter = entity;
        try {
            Long id = simpleJdbcTemplate.queryInsertReturningId(
                    SQL_INSERT,
                    chapter.getChapterTitle(),
                    chapter.getNumber(),
                    chapter.getTitle().getId()
            );
            chapter.setId(id);
        } catch (NoneUniqueValueException e) {
            chapter = null;
        }
        return chapter;
    }

    @Override
    public void update(Chapter entity) {
        simpleJdbcTemplate.query(
                SQL_UPDATE,
                entity.getChapterTitle(),
                entity.getNumber(),
                entity.getId()
        );
    }

    @Override
    public void delete(Chapter entity) {
        simpleJdbcTemplate.query(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<Chapter> findById(Long id) {
        Chapter chapter;
        try {
            List<Chapter> chapters = simpleJdbcTemplate.query(SQL_FIND_BY_ID, chapterRowMapper, id);
            if (!chapters.isEmpty()) {
                chapter = chapters.get(0);
            } else {
                chapter = null;
            }
        } catch (IllegalStateException e) {
            chapter = null;
        }
        return Optional.ofNullable(chapter);
    }

    @Override
    public List<Chapter> findAll() {
        return null;
    }
}
