package app;

import modules.*;
import app.tasks.*;

import com.google.inject.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        try {
            Injector injector = Guice.createInjector(new BasicModule());
            Task task = injector.getInstance(SimpleTask.class);
            task.run();
            System.out.println("Ready.");
        } catch (Exception ex) {
            System.err.println("caught exception: " + ex.getMessage());
        }
    }
}
