package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.api.Environment;

import com.typesafe.config.Config;
import actions.WithTimer;
import actions.WithDuplicateDetection;

public class FooBarController extends Controller {

    private Environment env;
    private Config config;

    @Inject
    public FooBarController(Environment env, Config config) {
        this.env = env;
        this.config = config;
    }

    @WithTimer
    @WithDuplicateDetection
    public Result list() {
        return ok(views.html.foobar.render(env));
    }
}
