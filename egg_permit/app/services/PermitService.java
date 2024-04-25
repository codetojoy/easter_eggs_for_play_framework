
package services;

import play.cache.SyncCacheApi;

import javax.inject.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class PermitService {
    private static int instanceCounter = 0;
    private final Logger logger = LoggerFactory.getLogger(getClass()) ;
    private final SyncCacheApi syncCacheApi;

    private static final String PERMITS_KEY_PREFIX = "system.permits";
    private static final int EXPIRATION_IN_SECONDS = 60;

    @Inject
    public PermitService(SyncCacheApi syncCacheApi) {
        this.syncCacheApi = syncCacheApi;
        logger.error("TRACER PermitService constructor syncCacheApi: " + syncCacheApi);
        instanceCounter++;
        logger.error("TRACER PermitService constructor # : " + instanceCounter);
    }

    public String buildKey(UUID permit) {
        return PERMITS_KEY_PREFIX + permit.toString();
    }

    public boolean usePermit(UUID permit) {
        boolean result = false;
        String key = buildKey(permit);
        if (syncCacheApi.get(key).isPresent()) {
            syncCacheApi.remove(key);
            result = true;
        }
        return result;
/*
        Optional<ConcurrentLinkedQueue<UUID>> maybePermits = syncCacheApi.get(PERMITS_KEY);
        if (maybePermits.isPresent()) {
            Queue<UUID> permits = maybePermits.get();
            if (permits.contains(permit)) {
                permits.remove(permit);
            }
        }
*/
    }

    public UUID getPermit() {
        UUID permit = UUID.randomUUID();
        String key = buildKey(permit);
        syncCacheApi.set(key, permit, EXPIRATION_IN_SECONDS);
        return permit;
/*
        Optional<ConcurrentLinkedQueue<UUID>> maybePermits = syncCacheApi.get(PERMITS_KEY);
        Queue<UUID> permits = null;
        if (maybePermits.isEmpty()) {
            permits = new ConcurrentLinkedQueue<UUID>();
            logger.error("TRACER PermitService creating permit queue");
        } else {
            permits = maybePermits.get();
            int numPermits = permits.size();
            logger.error("TRACER PermitService # permits: " + numPermits);
        }
        permits.add(permit);
        return lease;
*/
    }
}
