import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;

import java.lang.reflect.Method;

public class ActionCreator implements play.http.ActionCreator {
  private final static int LOG_THRESHOLD_IN_SECONDS = 1;

  @Override
  public Action createAction(Http.Request request, Method actionMethod) {
    return new Action.Simple() {
      @Override
      public CompletionStage<Result> call(Http.Request req) {
        Timer timer = new Timer(LOG_THRESHOLD_IN_SECONDS);

        return delegate.call(req).thenApply((result) -> {
            // timer
            if (timer.exceedsThreshold()) {
                String whoAmI = "ActionCreator";
                String reqStr = req.toString();
                System.out.println("TRACER: " + whoAmI + timer.getElapsed(reqStr));
            }
            return result;
        });
      }
    };
  }
}
