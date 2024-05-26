package modules;

import services.*;
import app.tasks.*;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        try {
            bind(SimpleTask.class).toConstructor(SimpleTask.class.getConstructor(BookService.class));
        } catch (Exception ex) {
            System.err.println("caught exception: " + ex.getMessage());
        }
    }
}
