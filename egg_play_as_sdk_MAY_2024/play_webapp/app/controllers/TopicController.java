package controllers;

import models.*;
import services.*;

import io.ebean.DB;

import javax.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.*;

import play.mvc.*;

public class TopicController extends Controller {
    private TopicService topicService;

    @Inject
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // This is not the "Play ethos" but we just want to test Ebean.
    // (In Play, we want to be async and query the DB on a different thread.)
    public Result list(Http.Request request) {
        List<Topic> topics = topicService.getTopics();
        return ok(views.html.topic.render(topics));
    }
}
