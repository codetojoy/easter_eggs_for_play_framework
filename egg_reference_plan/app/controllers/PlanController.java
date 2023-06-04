package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.PlanRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class PlanController extends Controller {

    private final PlanRepository planRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public PlanController(PlanRepository planRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.planRepository = planRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> quicklist(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return planRepository.getPlans().thenApplyAsync(plans -> {
            return ok(views.html.plans.render(plans));
        }, httpExecutionContext.current());
    }
}
