package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.ReferenceRepository;
import repository.ReferencePlanRepository;
import repository.CustomerReferenceRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class ReferenceController extends Controller {

    private final ReferenceRepository referenceRepository;
    private final ReferencePlanRepository referencePlanRepository;
    private final CustomerReferenceRepository customerReferenceRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public ReferenceController(ReferenceRepository referenceRepository,
                          ReferencePlanRepository referencePlanRepository,
                          CustomerReferenceRepository customerReferenceRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi) {
        this.referenceRepository = referenceRepository;
        this.referencePlanRepository = referencePlanRepository;
        this.customerReferenceRepository = customerReferenceRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> getReferences(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return referenceRepository.getReferences().thenApplyAsync(references -> {
            return ok(views.html.references.render(references));
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getReferencePlans(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return referencePlanRepository.getReferencePlans().thenApplyAsync(referencePlans -> {
            return ok(views.html.referenceplans.render(referencePlans));
        }, httpExecutionContext.current());
    }

    public CompletionStage<Result> getCustomerReferences(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return customerReferenceRepository.getCustomerReferences().thenApplyAsync(customerReferences -> {
            return ok(views.html.customerreferences.render(customerReferences));
        }, httpExecutionContext.current());
    }
}
