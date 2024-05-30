package app;

import app.tasks.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.ebean.Database;
import io.ebean.config.UnderscoreNamingConvention;

import java.util.*;
import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        try {
            DataSource dataSource = buildDataSource();
            Database database = Database.builder().dataSource(dataSource).namingConvention(new UnderscoreNamingConvention()).build();

            // -------------------------------------
            // change this:
            Task task = new SimpleEbeanTask();

            task.run();
        } catch (Exception ex) {
            System.err.println("caught exception: " + ex.getMessage());
        }
    }

    private static DataSource buildDataSource() {
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

