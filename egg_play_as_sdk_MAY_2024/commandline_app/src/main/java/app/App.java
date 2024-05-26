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
            DataSource dataSource = buildDataSource();
            Database database = Database.builder().dataSource(dataSource).namingConvention(new UnderscoreNamingConvention()).build();

            Application application = new GuiceApplicationBuilder().build();
            play.inject.Injector injector = application.injector();

            // -------------------------------------
            // change this:
            Task task = injector.instanceOf(SimpleEbeanTask.class);

            task.run();

            System.out.println("Ctrl-C to halt application");
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



