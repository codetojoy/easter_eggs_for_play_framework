package controllers;

import javax.inject.Inject;

import play.mvc.*;
import play.api.Environment;

import com.typesafe.config.Config;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
   
    private Environment env;
    private Config config;

    @Inject
    public HomeController(Environment env, Config config) {
        this.env = env;
        this.config = config;
    }

    private void testEnvVar(String key) {
        String msg = "";
        if (config.hasPath(key)) {
            msg = "key : " + key + " val: " + config.getString(key);
        } else {
            msg = "could not find key: " + key;
        }
        System.out.println("TRACER testEnvVar " + msg);
    }

    private void testEnvVars() {
        var keyFoo = "net.codetojoy.foo";
        var keyBar = "net.codetojoy.bar";
        var keyBaz = "net.codetojoy.baz";
        testEnvVar(keyFoo);
        testEnvVar(keyBar);
        testEnvVar(keyBaz);
    }

    public Result index() {
        testEnvVars();
        return ok(views.html.index.render(env));
    }
}
