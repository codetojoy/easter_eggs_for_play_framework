package controllers.api.widgets.bar;

import models.Bar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BarController extends Controller {
    private final List<Bar> bars;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public BarController() {
        this.bars = com.google.common.collect.Lists.newArrayList(
                new Bar("5150", "abc"),
                new Bar("6160", "def"),
                new Bar("7170", "ijk")
        );
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result listBars(Http.Request request) {
        return ok(views.html.listBars.render(bars, request));
    }
}
