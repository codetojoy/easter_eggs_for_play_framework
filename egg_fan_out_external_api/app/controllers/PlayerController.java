package controllers;

import models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Scala.asScala;

@Singleton
public class PlayerController extends Controller {

    private final Form<PlayerData> form;
    private MessagesApi messagesApi;
    private final List<Player> players;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public PlayerController(FormFactory formFactory, MessagesApi messagesApi) {
        this.form = formFactory.form(PlayerData.class);
        this.messagesApi = messagesApi;
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
        return ok(views.html.listPlayers.render(asScala(players), form, request, messagesApi.preferred(request)));
    }

    public Result createPlayer(Http.Request request) {
        final Form<PlayerData> boundForm = form.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("TRACER player errors = {}", boundForm.errors());
            return badRequest(views.html.listPlayers.render(asScala(players), boundForm, request, messagesApi.preferred(request)));
        } else {
            PlayerData data = boundForm.get();
            players.add(new Player(data.getName(), data.getStrategy(), data.getNumGames(), data.getNumWins()));
            return redirect(routes.PlayerController.listPlayers())
                .flashing("info", "TRACER Player added!");
        }
    }
}
