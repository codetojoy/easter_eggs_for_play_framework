package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import static play.libs.Scala.asScala;
import static play.libs.Scala.asJava;

import net.codetojoy.BuildInfo;

@Singleton
public class WidgetController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public WidgetController() {
    }

    public Result index() {
        System.out.println("TRACER APR 2024 BuildInfo : " + BuildInfo.toString());
        return ok(views.html.index.render());
    }
}
