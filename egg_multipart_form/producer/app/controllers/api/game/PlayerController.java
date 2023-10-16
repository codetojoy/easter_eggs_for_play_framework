package controllers.api.game;

import models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Scala.asScala;

@Singleton
public class PlayerController extends Controller {
    private final List<Player> players;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public PlayerController() {
        this.players = com.google.common.collect.Lists.newArrayList(
                new Player("Mozart", "max_card", 1, 1),
                new Player("Chopin", "nearest_card", 2, 2),
                new Player("Brahms", "min_card", 3, 3)
        );
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result listPlayers(Http.Request request) {
        return ok(views.html.listPlayers.render(players, request));
    }
}
