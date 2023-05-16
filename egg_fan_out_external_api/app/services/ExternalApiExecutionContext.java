
package services;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class ExternalApiExecutionContext extends CustomExecutionContext {
    @Inject
    public ExternalApiExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "my.external.api.pool");
    }
}

