package controllers.api.widgets.foo;

import models.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class FooController extends Controller {
    private final List<Foo> foos;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public FooController() {
        this.foos = com.google.common.collect.Lists.newArrayList(
                new Foo("Edward", "Smith"),
                new Foo("Randy", "Jones"),
                new Foo("Eric", "Edwards")
        );
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result listFoos(Http.Request request) {
        return ok(views.html.listFoos.render(foos, request));
    }
}
