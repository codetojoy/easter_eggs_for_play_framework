
package devtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import v1.post.PostResource;

import play.cache.SyncCacheApi;

import javax.inject.*;

import java.util.*;

public class DevToolsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SyncCacheApi syncCacheApi;

    private static final String DEV_TOOLS_TEST_DATA_KEY = "dev.tools.test.data.key";
    private static final int DEV_TOOLS_TEST_DATA_EXPIRY = 5_000_000;

    @Inject
    public DevToolsService(SyncCacheApi syncCacheApi) {
        this.syncCacheApi = syncCacheApi;
    }

    public void populateTestData() {
        System.out.println("TRACER populating test data");
        int numRecs = 50;
        var recs = new ArrayList<PostResource>();

        for (int i = 1; i <= numRecs; i++) {
            String id = "test-" + i; 
            String link = "";
            String title = "body test-" + i; 
            String body = "test body " + i;
            var pr = new PostResource(id, link, title, body);
            recs.add(pr);
        }
    
        syncCacheApi.set(DEV_TOOLS_TEST_DATA_KEY, recs, DEV_TOOLS_TEST_DATA_EXPIRY);
    }

    public List<PostResource> getTestData() {
        List<PostResource> results = List.of();

        Optional<List<PostResource>> resultsOption = syncCacheApi.get(DEV_TOOLS_TEST_DATA_KEY);

        if (resultsOption.isPresent()) {
            results = resultsOption.get();
        }

        System.out.println("TRACER get test data. size: " + results.size());

        return results;
    }    
}

