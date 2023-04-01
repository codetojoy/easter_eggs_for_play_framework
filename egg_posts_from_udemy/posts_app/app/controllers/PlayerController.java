package controllers;

import models.Player;
import models.PlayerRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link play.libs.concurrent.HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class PlayerController extends Controller {

    private final FormFactory formFactory;
    private final PlayerRepository playerRepository;
    private final HttpExecutionContext ec;

    @Inject
    public PlayerController(FormFactory formFactory, PlayerRepository playerRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.playerRepository = playerRepository;
        this.ec = ec;
    }

    public Result index(final Http.Request request) {
        return ok(views.html.player_home.render(request));
    }

    public CompletionStage<Result> addPlayer(final Http.Request request) {
        Player player = formFactory.form(Player.class).bindFromRequest(request).get();
        return playerRepository
                .add(player)
                .thenApplyAsync(p -> redirect(routes.PlayerController.index()), ec.current());
    }

    public CompletionStage<Result> getPlayers() {
        return playerRepository
                .list()
                .thenApplyAsync(playerStream -> ok(toJson(playerStream.collect(Collectors.toList()))), ec.current());
    }

}
