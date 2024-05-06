package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.LeagueRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class LeagueController extends Controller {

    private final LeagueRepository leagueRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public LeagueController(LeagueRepository leagueRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.leagueRepository = leagueRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> list(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return leagueRepository.getLeague().thenApplyAsync(leagueList -> {
            return ok(views.html.league.render(leagueList));
        }, httpExecutionContext.current());
    }
}
