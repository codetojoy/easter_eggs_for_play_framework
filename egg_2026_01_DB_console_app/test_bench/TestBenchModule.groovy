package test_bench

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
// import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig

import services.AccountService
import repository.AccountRepository

class TestBenchModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind repository and services
        bind(AccountRepository.class)
        bind(AccountService.class)
    }

    @Provides
    @Singleton
    Config provideConfig() {
        File confFile = new File("conf/application.conf")
        return ConfigFactory.parseFile(confFile)
                            .withFallback(ConfigFactory.load())
                            .resolve()
    }

    @Provides
    @Singleton
    Database provideDatabase(Config config) {
        // Extract database configuration
        String driver = config.getString("db.default.driver")
        String url = config.getString("db.default.url")
        String username = config.getString("db.default.username")
        String password = config.getString("db.default.password")

        // Configure datasource
        DataSourceConfig dsConfig = new DataSourceConfig()
        dsConfig.setDriver(driver)
        dsConfig.setUrl(url)
        dsConfig.setUsername(username)
        dsConfig.setPassword(password)

        // Configure Ebean database
        DatabaseConfig dbConfig = new DatabaseConfig()
        dbConfig.setName("default")
        dbConfig.setDefaultServer(true)
        dbConfig.setDataSourceConfig(dsConfig)
        dbConfig.addPackage("models")

        // Create and return the database
        Database database = DatabaseFactory.create(dbConfig)

        // Add shutdown hook to properly close database
        Runtime.runtime.addShutdownHook(new Thread() {
            @Override
            void run() {
                database.shutdown()
            }
        })

        return database
    }

    /*
    @Provides
    @Singleton
    ActorSystem provideActorSystem() {
        // Load the application.conf from the conf directory
        File confFile = new File("conf/application.conf")
        Config config = ConfigFactory.parseFile(confFile)
                                    .withFallback(ConfigFactory.load())

        ActorSystem actorSystem = ActorSystem.create("cli-system", config)

        // Add shutdown hook to properly terminate ActorSystem
        Runtime.runtime.addShutdownHook(new Thread() {
            @Override
            void run() {
                actorSystem.terminate()
            }
        })

        return actorSystem
    }
    */
}
