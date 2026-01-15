package cli

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.apache.pekko.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import services.ApiExecutionContext
import services.LongRunningExecutionContext
import services.AccountService

class CliModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind services
        bind(AccountService.class)
        bind(ApiExecutionContext.class)
        bind(LongRunningExecutionContext.class)
    }

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
}
