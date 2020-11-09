//package ru.itis.aivar.servlets.test;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//import java.io.*;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@WebServlet("/image")
//@MultipartConfig
//public class ImageServlet extends HttpServlet {
//
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/jsp/test/image-test.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Part> parts = req.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
//        for (Part part : parts) {
//            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
//            InputStream imageContent = part.getInputStream();
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//            int nRead;
//            byte[] data = new byte[1024];
//            while ((nRead = imageContent.read(data, 0, data.length)) != -1){
//                buffer.write(data, 0, nRead);
//            }
//            buffer.flush();
//            byte[] byteArray = buffer.toByteArray();
//            String currentPath = getServletContext().getRealPath("");
//            File imageFile = new File(String.valueOf(Paths.get(currentPath).resolve(imageName)));
//            System.out.println(imageFile);
//            boolean created = imageFile.createNewFile();
//            if (created){
//                OutputStream outputStream = new FileOutputStream(imageFile);
//                outputStream.write(byteArray);
//            }
//        }
//    }
//}
