package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;

import static play.libs.Scala.asScala;

import play.libs.ws.*;
import play.libs.concurrent.*;

@Singleton
public class SandboxController extends Controller {

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;
    private List<SandboxData> sandboxInfo = new ArrayList<>();

    private HttpExecutionContext ec;
    private WSClient ws;

    @Inject
    public SandboxController(HttpExecutionContext ec, WSClient ws) {
        this.ec = ec;
        this.ws = ws;

        SandboxData sandbox1 = new SandboxData();
        sandbox1.setName("name-1");
        sandbox1.setResult("test-result-a");
        sandbox1.setExecutionTime("1");

        SandboxData sandbox2 = new SandboxData();
        sandbox2.setName("name-2");
        sandbox2.setResult("test-result-b");
        sandbox2.setExecutionTime("2");

        SandboxData sandbox3 = new SandboxData();
        sandbox3.setName("name-3");
        sandbox3.setResult("test-result-c");
        sandbox3.setExecutionTime("1");

        sandboxInfo.add(sandbox1);
        sandboxInfo.add(sandbox2);
        sandboxInfo.add(sandbox3);
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> fanOut(Http.Request request) {
        String myUrl = "http://localhost:8080/waro/strategy?prize_card=10&max_card=12&mode=max&cards=4&cards=6&cards=2&delay_in_seconds=5";
        CompletableFuture<WSResponse> request1 = ws.url(myUrl).get().toCompletableFuture();
        CompletableFuture<WSResponse> request2 = ws.url(myUrl).get().toCompletableFuture();
        CompletableFuture<WSResponse> request3 = ws.url(myUrl).get().toCompletableFuture();
        CompletableFuture<WSResponse> request4 = ws.url(myUrl).get().toCompletableFuture();

        /*
        List<CompletableFuture<WSResponse>> futures = new ArrayList<>();
        futures.add(request1);
        futures.add(request2);
        futures.add(request3);
        futures.add(request4);
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futures);
       */
        return ws.url(myUrl).get().thenApply(response -> {
            var json = response.asJson();
            System.out.println("TRACER received json: " + json.toString());

            String message = json.get("message").asText();
            String result = json.get("card").asText();

            SandboxData sandboxData = new SandboxData();
            sandboxData.setName(message);
            sandboxData.setResult(result);
            sandboxInfo.add(sandboxData);

            return ok(views.html.sandbox.render(sandboxInfo));
        });
    }
}
