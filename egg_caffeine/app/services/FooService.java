
package services;

import play.cache.SyncCacheApi;

import javax.inject.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class FooService extends BaseFooService {
    private static int instanceCounter = 0;
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;
    private final SyncCacheApi syncCacheApi;

    private static final String EXTERNAL_API_KEY = "my.external.api.key";

    @Inject
    public FooService(ConfigService configService, SyncCacheApi syncCacheApi) {
        super(configService);
        this.syncCacheApi = syncCacheApi;
        logger.error("TRACER FooService constructor syncCacheApi: " + syncCacheApi);
        instanceCounter++;
        logger.error("TRACER FooService constructor # : " + instanceCounter);
    }

    public boolean isEnabled() {
        return true;
    }

    public String externalApiCall(final int id) {
        Callable<String> fetcher = () -> {  
            return fetchExternalApiCall(id);
        };
        String result = syncCacheApi.getOrElseUpdate(EXTERNAL_API_KEY, fetcher);
        return result;
    }

    private String fetchExternalApiCall(int id) {
        // pathogen
        logger.error("TRACER calling 'expensive' external API !");
        int delayInMillis = super.getDelayInMillis();
        try { Thread.sleep(delayInMillis); } catch (Exception ex) {}
        return "" + id + "-" + new java.util.Date().toString();
    }

    /*
     * silly method
     * param input string
     *
     * return string with no spaces and doubled !
     * e.g. "abc def!" => "abcdef!!"
     */
    public String foo(String s) {
        StringBuilder builder = new StringBuilder();

        if (s != null) {
            if (!s.isEmpty()) {
                // 'char' is a String here
                String[] chars = s.split("");

                for (var c : chars) {
                    if (! c.equals(" ")) {
                        builder.append(c);
                    } else if (c.equals("!")) {
                        builder.append(c);
                        builder.append(c);
                    }
                }
            } else {
                System.err.println("WARN empty arg");
            }
        } else {
            throw new IllegalArgumentException("null arg");
        }

        return builder.toString();
    }
}
