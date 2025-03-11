
package services;

import org.apache.pekko.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;

public class AccountApiExecutionContext extends CustomExecutionContext {
    private static final String ACCOUNT_API_POOL = "account.api.pool";

    @Inject
    public AccountApiExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, ACCOUNT_API_POOL);
    }
}

