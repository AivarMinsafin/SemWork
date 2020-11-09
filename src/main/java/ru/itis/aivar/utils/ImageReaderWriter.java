package ru.itis.aivar.utils;

import ru.itis.aivar.models.Page;
import ru.itis.aivar.models.Title;

import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ImageReaderWriter {
    void save(List<Part> parts, String targetDir) throws IOException;
    void save(Part part, String targetDir) throws IOException;
    void writeToOutputStream(String imagePath, OutputStream outputStream) throws IOException;
}
