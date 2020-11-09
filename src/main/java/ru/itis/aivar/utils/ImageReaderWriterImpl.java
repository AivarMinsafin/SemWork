package ru.itis.aivar.utils;

import ru.itis.aivar.exceptions.imagereadwriter.CanNotCreateImageException;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class ImageReaderWriterImpl implements ImageReaderWriter {

    public void save(List<Part> parts, String targetDir) throws IOException {
        for (int i = 0; i < parts.size(); i++) {
            save(parts.get(i), targetDir);
        }
    }

    @Override
    public void save(Part part, String targetDir) throws IOException {
        String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        InputStream imageContent = part.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = imageContent.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        buffer.close();
        File imageFile = new File(String.valueOf(Paths.get(targetDir).resolve(imageName)));
        boolean created = imageFile.createNewFile();
        imageContent.close();
        if (created) {
            OutputStream outputStream = new FileOutputStream(imageFile, false);
            outputStream.write(byteArray);
            outputStream.close();
        }else {
            throw new CanNotCreateImageException();
        }
    }

    @Override
    public void writeToOutputStream(String imagePath, OutputStream outputStream) throws IOException {
        InputStream imageContent = new FileInputStream(imagePath);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = imageContent.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        outputStream.write(byteArray);
        outputStream.close();
        imageContent.close();
    }


}
