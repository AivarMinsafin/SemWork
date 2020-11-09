package ru.itis.aivar.repositories.filerepo;

import org.apache.commons.io.FileUtils;
import ru.itis.aivar.exceptions.CannotCreateChapterException;
import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.repositories.abstracts.filerepo.ChapterRepositoryFileSystem;
import ru.itis.aivar.utils.ImageReaderWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ChapterRepositoryFileSystemImpl implements ChapterRepositoryFileSystem {


    private Properties properties;
    private File titlesDir;

    public ChapterRepositoryFileSystemImpl(Properties properties) {
        this.properties = properties;
        titlesDir = new File(properties.getProperty("titles-dir"));
    }

    @Override
    public void save(Chapter chapter) {
        File chDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir))
                .resolve(String.valueOf(chapter.getTitle().getId())).resolve(String.valueOf(chapter.getId()))));
        boolean created = chDir.mkdir();
        if (!created){
            throw new CannotCreateChapterException();
        }
    }

    @Override
    public void delete(Chapter chapter) {
        File chDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir))
                .resolve(String.valueOf(chapter.getId()))));
        try {
            FileUtils.deleteDirectory(chDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
