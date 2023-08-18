package devtools;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import play.cache.SyncCacheApi;

public class DevToolsController extends Controller {

    private HttpExecutionContext ec;
    private SyncCacheApi syncCacheApi;
    private DevToolsService devToolsService;

    @Inject
    public DevToolsController(HttpExecutionContext ec, 
                            SyncCacheApi syncCacheApi, DevToolsService devToolsService) {
        this.ec = ec;
        this.syncCacheApi = syncCacheApi;
        this.devToolsService = devToolsService;
    }

    public Result initial(Http.Request request) {
        return ok(views.html.devtools.render("input_mode"));
    }

    public Result apply(Http.Request request) {
        devToolsService.populateTestData();
        return ok(views.html.devtools.render("applied"));
    }
}
