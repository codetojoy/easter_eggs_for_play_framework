package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;

public class HomeController extends Controller {
    @Inject
    public HomeController() {
    }

    public Result index() {
        return ok(views.html.index.render());
    }
}
