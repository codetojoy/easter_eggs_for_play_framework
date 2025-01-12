package controllers;

import java.util.*;

import play.mvc.*;
import play.db.evolutions.Evolutions;

import javax.inject.Inject;

public class EvolutionsController extends Controller {

    @Inject
    public EvolutionsController() {
    }

    protected void logEvolutions(String evolutionName) {
        var evolutionsReader = Evolutions.fromClassLoader();
        var evolutionsSeq = evolutionsReader.evolutions(evolutionName);
        var evolutions = scala.collection.JavaConverters.seqAsJavaList(evolutionsSeq);

        for (var evolution : evolutions) {
            var revision = evolution.revision();
            var sqlUp = evolution.sql_up();
            var sqlDown = evolution.sql_down();
            var hash = evolution.hash();
            System.out.println("revision: " + revision + " hash: " + hash);
        }
        System.out.println("TRACER " + new Date() + " -- evolutions OK");
    }

    public Result listDefault() {
        logEvolutions("default");
        return ok(views.html.evolutions.render());
    }

    public Result list(Http.Request request, String evolutionName) {
        logEvolutions(evolutionName);
        return ok(views.html.evolutions.render());
    }
}

