package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.api.Environment;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
   
    private Environment env;

    @Inject
    public HomeController(Environment env) {
        this.env = env;
    }

    public Result index() {
        return ok(views.html.index.render(env));
    }
}
