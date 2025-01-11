package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;
import repository.LeagueRepository;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class LeagueController extends Controller {

    private final LeagueRepository leagueRepository;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public LeagueController(LeagueRepository leagueRepository,
                          ClassLoaderExecutionContext classLoaderExecutionContext,
                          MessagesApi messagesApi) {
        this.leagueRepository = leagueRepository;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> list(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return leagueRepository.getLeague().thenApplyAsync(leagueList -> {
            return ok(views.html.league.render(leagueList));
        }, classLoaderExecutionContext.current());
    }
}
