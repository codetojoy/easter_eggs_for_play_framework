import play.mvc.Action;
import play.mvc.Http;

import java.lang.reflect.Method;

public class ActionCreator implements play.http.ActionCreator {
  @Override
  public Action createAction(Http.Request request, Method actionMethod) {
    return new TimerAction();
  }
}
