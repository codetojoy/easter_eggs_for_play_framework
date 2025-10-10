package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.api.Environment;

import com.typesafe.config.Config;

public class FooBarController extends Controller {
   
    private Environment env;
    private Config config;

    @Inject
    public FooBarController(Environment env, Config config) {
        this.env = env;
        this.config = config;
    }

    public Result list() {
        return ok(views.html.foobar.render(env));
    }
}
