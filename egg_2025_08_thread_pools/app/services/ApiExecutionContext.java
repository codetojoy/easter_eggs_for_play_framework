
package services;

import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class ApiExecutionContext extends CustomExecutionContext {
    private static final String API_POOL = "api.pool";

    @Inject
    public ApiExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, API_POOL);
    }
}

