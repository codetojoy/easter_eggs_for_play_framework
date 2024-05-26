package app;

import modules.*;
import app.tasks.*;

import com.google.inject.*;
import java.util.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.ebean.Database;
import io.ebean.config.UnderscoreNamingConvention;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        try {
            App app = new App();
            app.initDB();

            Injector injector = Guice.createInjector(new BasicModule());
            Task task = injector.getInstance(SimpleTask.class);
            task.run();
            System.out.println("Ready.");
        } catch (Exception ex) {
            System.err.println("caught exception: " + ex.getMessage());
        }
    }

    private void initDB() {
        DataSource dataSource = buildDataSource();
        Database database = Database.builder().dataSource(dataSource).namingConvention(new UnderscoreNamingConvention()).build();
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
