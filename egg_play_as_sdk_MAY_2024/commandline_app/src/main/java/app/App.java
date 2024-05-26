package app;

import models.*;
import app.tasks.*;
import services.*;

import com.google.inject.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.ebean.Database;
import io.ebean.config.UnderscoreNamingConvention;

import java.util.*;
import javax.sql.DataSource;

import play.*;
import play.inject.guice.GuiceApplicationBuilder;

public class App {
    public static void main(String[] args) {
        try {
            // -------------------------------------
            // change this:

            App app = new App();
            DataSource dataSource = app.buildDataSource();
            Database database = Database.builder().dataSource(dataSource).namingConvention(new UnderscoreNamingConvention()).build();

            // Injector injector = Guice.createInjector();
            // BookService bookService = injector.getInstance(BookService.class);

            Application application = new GuiceApplicationBuilder().build();
            play.inject.Injector injector = application.injector();

            Task task = injector.instanceOf(SimpleBookServiceTask.class);
            task.run(database);
            
/*
            BookService bookService = injector.instanceOf(BookService.class);
            System.out.println("TRACER found bookService? " + bookService);
            List<Book> books = bookService.getBooks();
            System.out.println("TRACER found # books: " + books.size());
            // Task task = new SimpleTask();

            // -------------------------------------
            // App app = new App();
            // app.run(task);
*/
        } catch (Exception ex) {
            System.err.println("caught exception: " + ex.getMessage());
        }
    }

    private void run(Task task) {
        DataSource dataSource = buildDataSource();
        Database database = Database.builder().dataSource(dataSource).namingConvention(new UnderscoreNamingConvention()).build();
        boolean result = task.run(database);
        System.out.println("TRACER App.run RESULT: " + result);

        System.out.println("database shutdown...");
        database.shutdown();
        System.out.println("dataSource close...");
        ((HikariDataSource) dataSource).close();
        System.out.println("Ready.");
    }

    private DataSource buildDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(System.getenv("DB_URL"));
        hikariConfig.setUsername(System.getenv("DB_USERNAME"));
        hikariConfig.setPassword(System.getenv("DB_PASSWORD"));
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        hikariConfig.setAutoCommit(false);

        return new HikariDataSource(hikariConfig);
    }
}



