package ru.itis.aivar.repositories.filerepo;

import org.apache.commons.io.FileUtils;
import ru.itis.aivar.exceptions.TitleIsNotCreatedInFileSystemException;
import ru.itis.aivar.exceptions.imagereadwriter.CanNotCreateImageException;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.repositories.abstracts.filerepo.TitleRepositoryFileSystem;
import ru.itis.aivar.utils.ImageReaderWriter;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class TitleRepositoryFileSystemImpl implements TitleRepositoryFileSystem {

    private Properties properties;
    private ImageReaderWriter imageReaderWriter;
    private File titlesDir;

    public TitleRepositoryFileSystemImpl(Properties properties, ImageReaderWriter imageReaderWriter) {
        this.properties = properties;
        this.imageReaderWriter = imageReaderWriter;
        titlesDir = new File(properties.getProperty("titles-dir"));
    }

    @Override
    public void save(Title title, Part part) {
        File titleFolder = new File(String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(String.valueOf(title.getId()))));
        boolean created = titleFolder.mkdir();
        if (created) {
            try {
                imageReaderWriter.save(part, String.valueOf(titleFolder));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new TitleIsNotCreatedInFileSystemException();
        }
    }

    @Override
    public void delete(Title title) {
        File titleDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(String.valueOf(title.getId()))));
        try {
            FileUtils.deleteDirectory(titleDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Title title, Part part) {
        File titleDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(String.valueOf(title.getId()))));
        if (part != null) {
            try {
                File toDelete = FileUtils.getFile(String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(title.getTitleImg())));
                FileUtils.forceDelete(toDelete);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                imageReaderWriter.save(part, String.valueOf(titleDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
