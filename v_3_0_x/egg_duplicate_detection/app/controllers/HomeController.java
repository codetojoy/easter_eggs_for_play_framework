package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.api.Environment;

import com.typesafe.config.Config;

public class HomeController extends Controller {
   
    private Environment env;
    private Config config;

    @Inject
    public HomeController(Environment env, Config config) {
        this.env = env;
        this.config = config;
    }

    public Result index() {
        return ok(views.html.index.render(env));
    }
}
