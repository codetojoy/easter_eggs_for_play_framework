package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class DuplicateDetectionAction extends Action.Simple {
  private static final long DETECTION_WINDOW_MS = 1000; // 1 second
  private static final Map<String, Long> lastRequestTimes = new ConcurrentHashMap<>();

  @Override
  public CompletionStage<Result> call(Http.Request req) {
    String endpoint = req.method() + " " + req.path();
    long currentTime = System.currentTimeMillis();

    Long lastRequestTime = lastRequestTimes.get(endpoint);

    if (lastRequestTime != null) {
      long timeSinceLastRequest = currentTime - lastRequestTime;

      if (timeSinceLastRequest < DETECTION_WINDOW_MS) {
        System.out.println("TRACER -> DUPLICATE DETECTION: " + endpoint +
                         " called twice within " + timeSinceLastRequest + "ms" +
                         " (threshold: " + DETECTION_WINDOW_MS + "ms)");
      }
    }

    lastRequestTimes.put(endpoint, currentTime);

    return delegate.call(req);
  }
}
