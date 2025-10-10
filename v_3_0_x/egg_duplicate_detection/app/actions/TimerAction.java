package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;

public class TimerAction extends Action.Simple {
  private final static int LOG_THRESHOLD_IN_SECONDS = 0;

  @Override
  public CompletionStage<Result> call(Http.Request req) {
    Timer timer = new Timer(LOG_THRESHOLD_IN_SECONDS);

    return delegate.call(req).thenApply((result) -> {
        // timer
        if (timer.exceedsThreshold()) {
            String whoAmI = "TimerAction";
            String reqStr = req.toString();
            System.out.println("TRACER: " + whoAmI + timer.getElapsed(reqStr));
        }
        return result;
    });
  }
}
