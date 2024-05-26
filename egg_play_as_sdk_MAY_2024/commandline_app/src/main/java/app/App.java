package app;

import models.*;
import app.tasks.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.ebean.Database;
import io.ebean.config.UnderscoreNamingConvention;

import javax.sql.DataSource;

import java.util.*;

public class App {
    public static void main(String[] args) {
        try {
            // -------------------------------------
            // change this:
            Task task = new SimpleTask();

            // -------------------------------------
            App app = new App();
            app.run(task);
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



