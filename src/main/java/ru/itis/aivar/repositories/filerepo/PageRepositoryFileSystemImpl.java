package ru.itis.aivar.repositories.filerepo;

import org.apache.commons.io.FileUtils;
import ru.itis.aivar.models.Chapter;
import ru.itis.aivar.models.Page;
import ru.itis.aivar.repositories.abstracts.PageRepository;
import ru.itis.aivar.repositories.abstracts.filerepo.PageRepositoryFileSystem;
import ru.itis.aivar.utils.ImageReaderWriter;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.*;

public class PageRepositoryFileSystemImpl implements PageRepositoryFileSystem {

    private File titlesDir;
    private Properties properties;
    private ImageReaderWriter imageReaderWriter;

    public PageRepositoryFileSystemImpl(Properties properties, ImageReaderWriter imageReaderWriter) {
        this.properties = properties;
        this.imageReaderWriter = imageReaderWriter;
        titlesDir = new File(properties.getProperty("titles-dir"));
    }


    @Override
    public void writeToOutputStream(Page page, OutputStream outputStream) {
        String imagePath = String.valueOf(Paths.get(String.valueOf(titlesDir))
                .resolve(page.getImagePath()));
        try {
            imageReaderWriter.writeToOutputStream(imagePath, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPagePath(Page page) {
        return String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(page.getImagePath()));
    }

    @Override
    public void save(Page page, Part part) {
        File imgDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir))
                .resolve(String.valueOf(page.getChapter().getTitle().getId()))
                .resolve(String.valueOf(page.getChapter().getId()))));
        try {
            imageReaderWriter.save(part, String.valueOf(imgDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePages(Chapter chapter) {
        File chDir = new File(String.valueOf(Paths.get(String.valueOf(titlesDir)).resolve(String.valueOf(chapter.getTitle().getId())).resolve(String.valueOf(chapter.getId()))));
        try {
            FileUtils.deleteDirectory(chDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
