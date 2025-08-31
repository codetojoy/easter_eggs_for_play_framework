
package services;

import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class LongRunningExecutionContext extends CustomExecutionContext {
    private static final String LONG_RUNNING_POOL = "longrunning.api.pool";

    @Inject
    public LongRunningExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, LONG_RUNNING_POOL);
    }
}

