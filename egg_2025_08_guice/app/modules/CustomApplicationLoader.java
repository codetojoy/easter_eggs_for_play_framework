package modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import play.ApplicationLoader;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;

import static services.sandbox.util.MyLog.buildLog;

public class CustomApplicationLoader extends GuiceApplicationLoader {
    @Override
    public GuiceApplicationBuilder builder(ApplicationLoader.Context context) {
        // Create a separate injector for early services only
        Injector injector = Guice.createInjector(new InfrastructureModule(), new BusinessLogicModule());
        
         // Verify BusinessLogicModule validation
        String validation = injector.getInstance(Key.get(String.class, Names.named("module.business.validation")));
        System.out.println(buildLog("BusinessLogicModule validation result: " + validation));
        
        return super.builder(context);
    }
}
