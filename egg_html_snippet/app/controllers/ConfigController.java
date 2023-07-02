package controllers;

import services.ConfigService;
import utilities.Timer;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.ConfigRepository;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;

public class ConfigController extends Controller {

    private final ConfigRepository configRepository;
    private final HttpExecutionContext httpExecutionContext;
    private final MessagesApi messagesApi;
    private final ConfigService configService;

    @Inject
    public ConfigController(ConfigRepository configRepository,
                          HttpExecutionContext httpExecutionContext,
                          MessagesApi messagesApi,
                          ConfigService configService) {
        this.configRepository = configRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.messagesApi = messagesApi;
        this.configService = configService;
    }

    public CompletionStage<Result> showSnippet(Http.Request request) {
        int docId = 5150;
        return configRepository.getConfig(docId).thenApplyAsync(config -> {
            String snippet = config.getSnippet();
            String template = config.getTemplate();
            String hash = config.getHash();
            Optional<String> result = configService.buildSnippet(snippet, template, hash);

            String message = "error";

            if (result.isPresent()) {
                message = result.get();
            } 

            return ok(views.html.snippet.render(message));
        }, httpExecutionContext.current());
    }
}
