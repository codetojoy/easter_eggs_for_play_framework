package controllers;

import services.MyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Scala.asScala;

@Singleton
public class MyController extends Controller {
    private MessagesApi messagesApi;
    private MyService myService;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public MyController(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    public Result index(Http.Request request) {
        Messages messages = messagesApi.preferred(request);
        return ok(views.html.mine.render(messages));
    }
}
