package modules;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.ApplicationLoader;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;

public class CustomApplicationLoader extends GuiceApplicationLoader {

    @Override
    public GuiceApplicationBuilder builder(ApplicationLoader.Context context) {
        System.out.println("TRACER HELLO FROM CAL");
        return initialBuilder;
    }
}
