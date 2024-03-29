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

import play.twirl.api.Html;

public class PlanController extends Controller {

    private final PlanRepository planRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    private final List<String> availableLanguages;

    @Inject
    public PlanController(PlanRepository planRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.planRepository = planRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
        this.availableLanguages = List.of("Arabic","Chinese","English","French","German","Spanish","Ukrainian");
    }

    public CompletionStage<Result> viewplan(Http.Request request, Long id) throws Exception {
        CompletionStage<Integer> countFuture = planRepository.getPlanCount();
        CompletionStage<List<Plan>> plansFuture = planRepository.getPlans();

        return countFuture.thenCombine(plansFuture, (count, plans) -> {
            Plan plan = null;

            for (var p : plans) {
                if (p.getId() == id) {
                    plan = p;
                }
            } 

            String message = "successful";

            return ok(views.html.viewplan.render(plan, message, buildHtml(availableLanguages), buildHtml(plan.getPayload())));
        });
    }

    public CompletionStage<Result> updateplan(Http.Request request, Long id) throws Exception {
        System.out.println("TRACER hello from updateplan!");
        var formMap = request.body().asFormUrlEncoded();

        String planName = null;
        String[] payload = null;

        if (formMap.keySet().contains("plan-name")) {
            planName = formMap.get("plan-name")[0];
            System.out.println("TRACER received plan-name: " + planName);
        } 

        if (formMap.keySet().contains("payload")) {
            payload = formMap.get("payload");
            System.out.println("TRACER received payload: " + payload);
        }

        List<String> payloadItems = Arrays.asList(payload);
        CompletionStage<Plan> planFuture = planRepository.updatePlan(id, planName, payloadItems);

        return planFuture.thenApply((plan) -> {
            String message = "successful";

            return ok(views.html.viewplan.render(plan, message, buildHtml(availableLanguages), buildHtml(plan.getPayload())));
        });
    }

    public CompletionStage<Result> augustlist(Http.Request request) throws Exception {
        Timer timer = new Timer();
        CompletionStage<Integer> countFuture = planRepository.getPlanCount();
        CompletionStage<List<Plan>> plansFuture = planRepository.getPlans();

        return countFuture.thenCombine(plansFuture, (count, plans) -> {
            String tmpMessage = "TRACER AUG 26 : " + " rs #: " + plans.size() + " total #: " + count;
            String message = timer.getElapsed(tmpMessage); 

            StringBuilder jsonBuilder = new StringBuilder();
            int i = 0;
            int max = availableLanguages.size();
            jsonBuilder.append("[");
            for (var lang : availableLanguages) {
                jsonBuilder.append("\"" + lang + "\"");
                if (i < max - 1) {
                    jsonBuilder.append(",");
                }
                i++;
            }
            jsonBuilder.append("]");

            // MEGA-hack: use id #18 
            // TODO: use id in request and just show one  
            Html selectedLanguages = null;

            for (var plan : plans) {
                if (plan.getId().equals("18")) {
                    selectedLanguages = buildHtml(plan.getPayload());
                }
            }

            return ok(views.html.plans.render(plans, message, buildHtml(availableLanguages), selectedLanguages));
        });
    }

    private String buildJson(List<String> languages) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        int max = languages.size();
        builder.append("[");
        for (var lang : languages) {
            builder.append("\"" + lang + "\"");
            if (i < max - 1) {
                builder.append(",");
            }
            i++;
        }
        builder.append("]");

        return builder.toString();
    }

    private String buildJson(String[] languages) {
        return buildJson(Arrays.asList(languages));
    
    }

    private Html buildHtml(List<String> languages) {
        Html html = Html.apply(buildJson(languages));

        return html;
    }
}
