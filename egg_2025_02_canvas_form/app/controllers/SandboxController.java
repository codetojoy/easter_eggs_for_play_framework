package controllers;

import java.util.*;

import play.mvc.*;
import play.libs.*;

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

    public Result createForm(Http.Request request) {
        var formData = request.body().asMultipartFormData();
        emitLog("TRACER HELLO");

        /*
        String input1 = request.body().asFormUrlEncoded().get("input1")[0];
        emitLog("input1: " + input1);

        String input2 = request.body().asFormUrlEncoded().get("input2")[0];
        emitLog("input2: " + input2);

        String signText = request.body().asFormUrlEncoded().get("sign-text")[0];
        emitLog("sign-text: " + signText);

        String[] filenameArr = request.body().asFormUrlEncoded().get("filename");
        emitLog("filenameArr: " + filenameArr);
*/
        return ok(views.html.sandboxReceive.render());
    }

    public Result signature(Http.Request request) {
        var formData = request.body().asMultipartFormData();
        emitLog("TRACER HELLO from signature");

        String message = "{\"status\": \"OK\"}";
        return ok(Json.parse(message));
    }
}

