package controllers;

import akka.stream.javadsl.Source;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.*;
import play.libs.ws.*;

import javax.inject.*;

@Singleton
public class SandboxController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    private final static String CONSUME_URL = "http://localhost:5150/consume";
    private final static String PING_URL = "http://localhost:5150/ping";

    private final WSClient wsClient;

    @Inject
    public SandboxController(WSClient wsClient) {
        this.wsClient = wsClient;
    }
 
    public Result ping() throws Exception {
        WSResponse response = wsClient.url(PING_URL)
                                      .get()
                                      .toCompletableFuture()
                                      .get(12, TimeUnit.SECONDS);
        String message = response.getBody();
        return ok(views.html.sandbox.render(message));
    }

    public Result consume() throws Exception {
        var dataPart1 = new Http.MultipartFormData.DataPart("foo","bar");
        var dataPart2 = new Http.MultipartFormData.DataPart("name","evh");
        WSResponse response = wsClient.url(CONSUME_URL)
                                      .post(Source.from(Arrays.asList(dataPart1, dataPart2)))
                                      .toCompletableFuture()
                                      .get(12, TimeUnit.SECONDS);
        String message = response.getBody();
        return ok(views.html.sandbox.render(message));
    }
}
