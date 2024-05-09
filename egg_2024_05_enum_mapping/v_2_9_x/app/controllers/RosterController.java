package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;
import repository.RosterRepository;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class RosterController extends Controller {

    private final RosterRepository rosterRepository;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public RosterController(RosterRepository rosterRepository,
                          ClassLoaderExecutionContext classLoaderExecutionContext,
                          MessagesApi messagesApi) {
        this.rosterRepository = rosterRepository;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> list(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return rosterRepository.getRoster().thenApplyAsync(rosterList -> {
            return ok(views.html.roster.render(rosterList));
        }, classLoaderExecutionContext.current());
    }
}
