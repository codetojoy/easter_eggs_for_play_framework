package controllers;

import utilities.Timer;
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
import java.util.concurrent.*;

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

    public CompletionStage<Result> quicklist(Http.Request request) throws Exception {
        boolean targetIsFoo = true;

        Timer timer = new Timer();
        CompletionStage<Integer> countFuture = planRepository.getPlanCount(targetIsFoo);
        CompletionStage<List<Plan>> plansFuture = planRepository.getPlans(targetIsFoo);

        return countFuture.thenCombine(plansFuture, (count, plans) -> {
            String tmpMessage = "isFoo: " + targetIsFoo + " rs #: " + plans.size() + " total #: " + count;
            String message = timer.getElapsed(tmpMessage); 
            return ok(views.html.plans.render(plans, message));
        });
    }
}
