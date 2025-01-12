package controllers;

import models.EvolutionInfo;

import java.util.*;

import play.mvc.*;
import play.db.evolutions.Evolutions;

import javax.inject.Inject;

public class EvolutionsController extends Controller {
    private static final String DEFAULT = "default";

    @Inject
    public EvolutionsController() {
    }

    protected String getPrefix(String s) {
        return s.substring(0, 10);
    }

    protected List<EvolutionInfo> buildEvolutions(String evolutionName) {
        var results = new ArrayList<EvolutionInfo>();
        var evolutionsReader = Evolutions.fromClassLoader();
        var evolutionsSeq = evolutionsReader.evolutions(evolutionName);
        var evolutions = scala.collection.JavaConverters.seqAsJavaList(evolutionsSeq);

        for (var evolution : evolutions) {
            var revision = evolution.revision();
            var sqlUp = evolution.sql_up();
            var sqlDown = evolution.sql_down();
            var hash = evolution.hash();
            System.out.println("revision: " + revision + " hash: " + hash);

            results.add(new EvolutionInfo(revision, sqlUp, sqlDown, hash));
        }
        System.out.println("TRACER " + new Date() + " -- evolutions OK");
        return results;
    }

    public Result listDefault() {
        var evolutionInfos = buildEvolutions(DEFAULT);
        return ok(views.html.evolutions.render(DEFAULT, evolutionInfos));
    }

    public Result list(Http.Request request, String evolutionName) {
        var evolutionInfos = buildEvolutions(evolutionName);
        return ok(views.html.evolutions.render(evolutionName, evolutionInfos));
    }
}

