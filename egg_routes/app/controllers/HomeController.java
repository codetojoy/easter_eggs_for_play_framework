package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Singleton;

@Singleton
public class HomeController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    public HomeController() {
    }

    public Result index() {
        return ok(views.html.index.render());
    }
}
