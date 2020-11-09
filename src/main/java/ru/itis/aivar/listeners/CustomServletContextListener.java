package ru.itis.aivar.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.aivar.repositories.*;
import ru.itis.aivar.repositories.abstracts.*;
import ru.itis.aivar.repositories.abstracts.filerepo.ChapterRepositoryFileSystem;
import ru.itis.aivar.repositories.abstracts.filerepo.PageRepositoryFileSystem;
import ru.itis.aivar.repositories.abstracts.filerepo.TitleRepositoryFileSystem;
import ru.itis.aivar.repositories.filerepo.ChapterRepositoryFileSystemImpl;
import ru.itis.aivar.repositories.filerepo.PageRepositoryFileSystemImpl;
import ru.itis.aivar.repositories.filerepo.TitleRepositoryFileSystemImpl;
import ru.itis.aivar.services.*;
import ru.itis.aivar.services.abstracts.*;
import ru.itis.aivar.utils.ImageReaderWriter;
import ru.itis.aivar.utils.ImageReaderWriterImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

public class CustomServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(servletContext.getResourceAsStream("/WEB-INF/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Properties titleProperties = new Properties();
        try {
            titleProperties.load(servletContext.getResourceAsStream("/WEB-INF/titles.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbProperties.getProperty("db.jdbc-url"));
        hikariConfig.setDriverClassName(dbProperties.getProperty("db.driver-class-name"));
        hikariConfig.setUsername(dbProperties.getProperty("db.username"));
        hikariConfig.setPassword(dbProperties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(dbProperties.getProperty("db.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        UsersService usersService = new UserServiceImpl(usersRepository);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        SecurityService securityService = new SecurityServiceImpl(passwordEncoder, usersService);

        AuthorRepository authorRepository = new AuthorRepositoryJdbcImpl(dataSource);
        AuthorService authorService = new AuthorServiceImpl(authorRepository, usersService);

        TitleRepository titleRepository = new TitleRepositoryJdbcImpl(dataSource);
        ImageReaderWriter imageReaderWriter = new ImageReaderWriterImpl();
        TitleRepositoryFileSystem titleRepositoryFileSystem = new TitleRepositoryFileSystemImpl(titleProperties, imageReaderWriter);
        TitleService titleService = new TitleServiceImpl(titleRepository, authorService, titleRepositoryFileSystem);

        ChapterRepository chapterRepository = new ChapterRepositoryJdbcImpl(dataSource);
        ChapterRepositoryFileSystem chapterRepositoryFileSystem = new ChapterRepositoryFileSystemImpl(titleProperties);
        ChapterService chapterService = new ChapterServiceImpl(chapterRepository, chapterRepositoryFileSystem, titleService);

        PageRepository pageRepository = new PageRepositoryJdbcImpl(dataSource);
        PageRepositoryFileSystem pageRepositoryFileSystem = new PageRepositoryFileSystemImpl(titleProperties, imageReaderWriter);
        PageService pageService = new PageServiceImpl(pageRepository, pageRepositoryFileSystem, chapterService);

        servletContext.setAttribute("titlesDir", titleProperties.getProperty("titles-dir"));
        servletContext.setAttribute("securityService", securityService);
        servletContext.setAttribute("usersService", usersService);
        servletContext.setAttribute("authorService", authorService);
        servletContext.setAttribute("titleService", titleService);
        servletContext.setAttribute("pageService", pageService);
        servletContext.setAttribute("chapterService", chapterService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
