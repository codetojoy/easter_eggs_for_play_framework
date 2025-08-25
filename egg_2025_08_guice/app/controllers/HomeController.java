package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.api.libs.json.Json;

import services.sandbox.UserService;

public class HomeController extends Controller {
    private final UserService userService;

    @Inject
    public HomeController(UserService userService) {
        this.userService = userService; 
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result sandbox(Http.Request request) throws Exception {
        String userResult = userService.getUser("7170");

        String prettyUserResult = Json.prettyPrint(Json.parse(userResult));
        return ok(views.html.sandbox.render(prettyUserResult));
    }
}
