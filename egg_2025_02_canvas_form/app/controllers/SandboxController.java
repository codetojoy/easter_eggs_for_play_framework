package controllers;

import java.util.*;

import play.mvc.*;

import javax.inject.Inject;

public class SandboxController extends Controller {

    @Inject
    public SandboxController() {
    }

    public Result index() {
        return ok(views.html.sandbox.render());
    }

    protected void emitLog(String s) {
        String date = new Date().toString();
        System.out.println("TRACER [" + date + "] : " + s);
    }

    public Result sandboxReceive(Http.Request request) {
        String input1 = request.body().asFormUrlEncoded().get("input1")[0];
        emitLog("input1: " + input1);
        String input2 = request.body().asFormUrlEncoded().get("input2")[0];
        emitLog("input2: " + input2);
        return ok(views.html.sandboxReceive.render());
    }
}

