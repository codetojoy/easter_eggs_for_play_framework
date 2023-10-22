package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.*;
import repository.CustomerRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.CompletionStage;

public class CustomerController extends Controller {

    private final CustomerRepository customerRepository;
    private final ClassLoaderExecutionContext classLoaderExecutionContext;
    private final MessagesApi messagesApi;

    @Inject
    public CustomerController(CustomerRepository customerRepository,
                          ClassLoaderExecutionContext classLoaderExecutionContext,
                          MessagesApi messagesApi) {
        this.customerRepository = customerRepository;
        this.classLoaderExecutionContext = classLoaderExecutionContext;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> quicklist(Http.Request request) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return customerRepository.getCustomers().thenApplyAsync(customers -> {
            return ok(views.html.customers.render(customers));
        }, classLoaderExecutionContext.current());
    }
}
